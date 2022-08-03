wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"query_transactions\",\"params\":[{\"item\":{\"type\":\"Identity\",\"value\":\"0x001a4ff63598e43af9cd42324abb7657fa849c5bc3\"},\"asset_infos\":[{\"asset_type\":\"CKB\",\"udt_hash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\"}],\"pagination\":{\"cursor\":null,\"order\":\"desc\",\"limit\":\"0x32\",\"return_count\":false},\"structure_type\":\"DoubleEntry\"}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
