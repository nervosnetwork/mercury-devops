import unittest

from model.config import WrkConfig
from model.wrk import BenchmarkSuiteFactory


class WrkTest(unittest.TestCase):

    def setUp(self):
        config = WrkConfig(node_url="http://127.0.0.1:8116", seconds=[10], collections=[300], threads=16, timeout=1, script_dir="script/mainnet")
        self.config = config
        scripts = ["get_spent_transaction_double_entry.lua"]
        self.scripts = scripts

    def tearDown(self):
        pass

    def test_benchmark_suitek1(self):
        benchmark_suite = BenchmarkSuiteFactory.get_instance_by_config(self.config)
        benchmark_suite.exec()

    def test_benchmark_suitek2(self):
        benchmark_suite = BenchmarkSuiteFactory.get_instance_by_config_and_scripts(self.config, self.scripts)
        benchmark_suite.exec()

    @staticmethod
    def test_benchmark_suite_by_config():
        benchmark_suite = BenchmarkSuiteFactory.get_instance("testnet")
        benchmark_suite.exec()
