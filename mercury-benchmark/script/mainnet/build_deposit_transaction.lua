wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_dao_deposit_transaction\",\"params\":[{\"from\":{\"items\":[{\"type\":\"Address\",\"value\":\"ckb1qyq8jy6e6hu89lzwwgv9qdx6p0kttl4uax9srq9shl\"}],\"source\":\"Free\"},\"amount\":30000000000,\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
