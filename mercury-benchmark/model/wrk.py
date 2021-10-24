import datetime
import os

from model.config import WrkConfig


class WrkScript(object):
    def __init__(self, script_name, script_dir, config=WrkConfig):
        self.__script_name = script_name
        self.__script_dir = script_dir
        self.__config = config
        self.__wrk_script = 'wrk -t{} -c{} -d{}s -T{}s --script={} --latency {}'

    def get_script_path(self):
        return os.path.join(self.__script_dir, self.__script_name)

    def get_cmd(self, seconds=60, collections=300):
        path = self.get_script_path()
        return self.__wrk_script.format(self.__config.threads, collections, seconds, self.__config.timeout, path,
                                        self.__config.node_url)


class Wrk(WrkScript):

    def __init__(self, seconds, collections, script_name, script_dir, config=WrkConfig):
        self.__seconds = seconds
        self.__collections = collections
        self.__result = ""
        self.__markdown = "### {} seconds {} collections\n```shell\n\n {} \n\n```\n\n"

        super(Wrk, self).__init__(script_name, script_dir, config)

    def get_qps(self):
        if self.__result == "":
            return 0.0
        lines = self.__result.splitlines()
        qps = lines[len(lines) - 2]
        return float(qps.replace("Requests/sec:", "").strip())

    def get_markdown(self):
        return self.__markdown.format(self.__seconds, self.__collections, self.__result)

    def run(self):
        cmd = self.get_cmd(self.__seconds, self.__collections)
        process = os.popen(cmd)
        output = process.read()
        process.close()

        self.__result = output

    @property
    def seconds(self):
        return self.__seconds

    @property
    def collections(self):
        return self.__collections


class WrkGroup(WrkScript):
    def __init__(self, name, script_name, script_dir, config=WrkConfig):
        self.__name = name
        self.__group = []
        self.__markdown = "## {}\n### 压测脚本\n```lua\n{}\n```\n\n### 压测命令\n```shell\n$ {}\n" \
                          "```\n\n {} \n\n### 压测结果\n {} \n\n Implementation time period: {} \n\n"

        super(WrkGroup, self).__init__(script_name, script_dir, config)

    def get_script_file(self):
        path = self.get_script_path()
        with open(path, "r") as f:
            data = f.read()  # 读取文件
            return data

    def append(self, wrk=Wrk):
        self.__group.append(wrk)

    def get_markdown(self):
        startTime = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        data = ""
        for x in self.__group:
            if isinstance(x, Wrk):
                data = data + x.get_markdown()
        endTime = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        return self.__markdown.format(self.__name, self.get_script_file(), self.get_cmd(), data,
                               self.get_table(), startTime + " ~ " + endTime)

    def get_table(self):
        collection_group = {}
        seconds = {}
        for x in self.__group:
            if x.collections not in collection_group:
                collection_group[x.collections] = [Wrk]
            if x.seconds not in seconds:
                seconds[x.seconds] = x.seconds

            collection_group.get(x.collections).append(x)

        header = "|  | "
        header_separator = "| --- |"
        for x in seconds:
            header = header + str(x) + "seconds | "
            header_separator = header_separator + " --- |"

        table = header + "\n" + header_separator + "\n"

        for collections in collection_group.keys():
            line = "| " + str(collections) + " | "
            for x in collection_group.get(collections):
                if isinstance(x, Wrk):
                    line = line + str(x.get_qps()) + " | "

            table = table + line + "\n"

        return table

    def run(self):
        for x in self.__group:
            if isinstance(x, Wrk):
                x.run()


class BenchmarkSuite:
    def __init__(self, wrk_group_instance=[]):
        self.__wrk_group_instance = wrk_group_instance
        self.__now = datetime.datetime.now().strftime('%Y-%m-%d-%H.%M.%S.%f')

    def exec(self):
        data = ""
        for x in self.__wrk_group_instance:
            if isinstance(x, WrkGroup):
                x.run()
                data += x.get_markdown()

        self.__record_log(data)

    def __record_log(self, data):
        self.__check_dir()

        benchmark_logs = os.path.join(os.path.abspath(os.path.dirname(os.path.dirname(__file__))),
                                      "benchmark_logs/" + str(self.__now) + ".md")
        with open(benchmark_logs, 'w') as f:
            f.write(data)

    def __check_dir(self):
        log_dir_path = os.path.join(os.path.abspath(os.path.dirname(os.path.dirname(__file__))), "benchmark_logs")
        if not os.path.exists(log_dir_path):
            os.makedirs(log_dir_path)


class BenchmarkSuiteFactory(object):
    benchmarkSuite = None
    base_dir = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))

    @classmethod
    def get_instance(cls, network):
        if cls.benchmarkSuite is None:
            cls.__get_wrk_group_instance(WrkConfig.read_config(network))

        return cls.benchmarkSuite

    @classmethod
    def get_instance_by_config(cls, config=WrkConfig):
        if cls.benchmarkSuite is None:
            cls.__get_wrk_group_instance(config=config)

        return cls.benchmarkSuite

    @classmethod
    def get_instance_by_scripts(cls, network, scripts=[]):
        if cls.benchmarkSuite is None:
            cls.__get_wrk_group_instance(config=WrkConfig.read_config(network), scripts=scripts)

        return cls.benchmarkSuite

    @classmethod
    def get_instance_by_config_and_scripts(cls, config=WrkConfig, scripts=[]):
        if cls.benchmarkSuite is None:
            cls.__get_wrk_group_instance(config=config, scripts=scripts)

        return cls.benchmarkSuite

    @classmethod
    def __get_wrk_group_instance(cls, config=WrkConfig, scripts=[]):
        script_dir = os.path.join(cls.base_dir, config.script_dir)

        wrk_group_instance = []
        script_names = []
        if len(scripts) == 0:
            script_names = os.listdir(script_dir)
        else:
            for x in scripts:
                script_names.append(x)

        for x in script_names:
            group = WrkGroup(name=x, script_name=x, script_dir=script_dir, config=config)
            wrk_group_instance.append(group)
            for y in config.seconds:
                for z in config.collections:
                    wrk = Wrk(seconds=y, collections=z, script_name=x, script_dir=script_dir, config=config)
                    group.append(wrk)

        cls.benchmarkSuite = BenchmarkSuite(wrk_group_instance=wrk_group_instance)
