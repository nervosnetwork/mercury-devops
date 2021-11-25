wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_dao_withdraw_transaction\",\"params\":[{\"from\":{\"type\":\"Address\",\"value\":\"ckb1qyq8jy6e6hu89lzwwgv9qdx6p0kttl4uax9srq9shl\"},\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
