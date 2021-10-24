wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_withdraw_transaction\",\"params\":[{\"from\":{\"Address\":\"ckb1qyq8ze8534a9hu3fs9n03kqms84yayywz6ksflfvpk\"},\"pay_fee\":\"ckb1qyq8ze8534a9hu3fs9n03kqms84yayywz6ksflfvpk\",\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
