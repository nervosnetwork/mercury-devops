wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_adjust_account_transaction\",\"params\":[{\"item\":{\"type\":\"Address\",\"value\":\"ckb1qyq8jy6e6hu89lzwwgv9qdx6p0kttl4uax9srq9shl\"},\"from\":[],\"asset_info\":{\"asset_type\":\"UDT\",\"udt_hash\":\"0x613c88b598885bdec6dcc3d3687db5ae6be9a64e96e516c08e0bebe233d23304\"},\"account_number\":1,\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
