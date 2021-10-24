wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_transfer_transaction\",\"params\":[{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\"},\"from\":{\"items\":[{\"Address\":\"ckb1qyq8ze8534a9hu3fs9n03kqms84yayywz6ksflfvpk\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckb1qyqdmeuqrsrnm7e5vnrmruzmsp4m9wacf6vsxasryq\",\"amount\":\"10000000000\"}],\"mode\":\"HoldByFrom\"},\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end