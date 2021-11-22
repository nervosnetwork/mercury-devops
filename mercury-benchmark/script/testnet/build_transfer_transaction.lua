wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_transfer_transaction\",\"params\":[{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\"},\"from\":{\"items\":[{\"type\":\"Identity\",\"value\":\"0x00af0b41c627807fbddcee75afa174d5a7e5135ebd\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyqqtg06h75ymw098r3w0l3u4xklsj04tnsqctqrmc\",\"amount\":\"10000000000\"}],\"mode\":\"HoldByFrom\"},\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end