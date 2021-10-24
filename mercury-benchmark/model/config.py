import json
import os

class WrkConfig(object):
    def __init__(self, node_url, seconds, collections, threads, timeout, script_dir):
        self.__node_url = node_url
        self.__seconds = seconds
        self.__collections = collections
        self.__threads = threads
        self.__timeout = timeout
        self.__script_dir = script_dir

    @classmethod
    def read_config(cls, network="testnet"):
        path = os.path.join(os.path.abspath(os.path.dirname(os.path.dirname(__file__))), "config.json")
        with open(path, "r") as f:
            configs = json.load(f)

        if network not in configs:
            print("unsupported network type " + network)

        return WrkConfig(**configs[network])

    @property
    def node_url(self) -> str:
        return self.__node_url

    @property
    def seconds(self) -> []:
        return self.__seconds

    @property
    def collections(self) -> []:
        return self.__collections

    @property
    def threads(self) -> int:
        return self.__threads

    @property
    def timeout(self) -> int:
        return self.__timeout

    @property
    def script_dir(self) -> str:
        return self.__script_dir
