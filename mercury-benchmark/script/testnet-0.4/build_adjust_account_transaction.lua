wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_adjust_account_transaction\",\"params\":[{\"item\":{\"type\":\"Identity\",\"value\":\"0x001bb92ee59d1320867801eaa949e5322545c416b8\"},\"from\":[{\"type\":\"Identity\",\"value\":\"0x00202647fecc5b9d8cbdb4ae7167e40f5ab1e4baaf\"}],\"asset_info\":{\"asset_type\":\"UDT\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"account_number\":\"0x1\",\"fee_rate\":\"0x3e8\"}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
