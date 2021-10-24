wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"query_transactions\",\"params\":[{\"item\":{\"Address\":\"ckb1qyq8ze8534a9hu3fs9n03kqms84yayywz6ksflfvpk\"},\"asset_infos\":[{\"asset_type\":\"CKB\",\"udt_hash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\"}],\"pagination\":{\"cursor\":[127,255,255,255,255,255,255,254],\"order\":\"desc\",\"limit\":50,\"return_count\":false},\"structure_type\":\"Native\"}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end