import sys

from model.wrk import *


if __name__ == '__main__':
    argv = sys.argv[:]

    if len(argv) < 2:
        print("error: please set network type")
        sys.exit(1)

    network = argv[1]
    script_names = []
    if len(argv) != 2:
        for i in range(2, len(argv)):
            script_names.append(argv[i])

    benchmark_suite = BenchmarkSuiteFactory.get_instance_by_scripts(network, script_names)
    benchmark_suite.exec()
