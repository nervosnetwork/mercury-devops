wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_transfer_transaction\",\"params\":[{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\"},\"from\":[{\"type\":\"Identity\",\"value\":\"0x00af0b41c627807fbddcee75afa174d5a7e5135ebd\"}],\"to\":[{\"address\":\"ckt1qyqqtg06h75ymw098r3w0l3u4xklsj04tnsqctqrmc\",\"amount\":\"0x2540be400\"}],\"output_capacity_provider\":\"From\",\"pay_fee\":\"From\",\"fee_rate\":null}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
