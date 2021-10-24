wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_transfer_transaction\",\"params\":[{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\"},\"from\":{\"items\":[{\"Address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyqg88ccqm59ksxp85788pnqg4rkejdgcg2qxcu2qf\",\"amount\":\"10000000000\"}],\"mode\":\"HoldByFrom\"},\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end