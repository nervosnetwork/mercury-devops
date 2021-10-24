wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_smart_transfer_transaction\",\"params\":[{\"asset_info\":{\"asset_type\":\"UDT\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"from\":[\"ckt1qyqg88ccqm59ksxp85788pnqg4rkejdgcg2qxcu2qf\"],\"to\":[{\"address\":\"ckt1qyq27z6pccncqlaamnh8ttapwn260egnt67ss2cwvz\",\"amount\":\"20\"}],\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"


function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end