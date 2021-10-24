wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_adjust_account_transaction\",\"params\":[{\"item\":{\"Identity\":\"0x00839f1806e85b40c13d3c73866045476cc9a8c214\"},\"from\":[],\"asset_info\":{\"asset_type\":\"UDT\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"account_number\":1,\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end