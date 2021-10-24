wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_deposit_transaction\",\"params\":[{\"from\":{\"items\":[{\"Address\":\"ckb1qyq8ze8534a9hu3fs9n03kqms84yayywz6ksflfvpk\"}],\"source\":\"Free\"},\"amount\":30000000000,\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
