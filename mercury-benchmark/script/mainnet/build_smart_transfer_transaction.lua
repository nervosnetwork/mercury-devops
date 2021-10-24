wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_smart_transfer_transaction\",\"params\":[{\"asset_info\":{\"asset_type\":\"UDT\",\"udt_hash\":\"0x38928268eaffd58e25605a923cf61602e914cb8365ba00131ec0bc004cc753d1\"},\"from\":[\"ckb1qyq8ze8534a9hu3fs9n03kqms84yayywz6ksflfvpk\"],\"to\":[{\"address\":\"ckb1qyqdmeuqrsrnm7e5vnrmruzmsp4m9wacf6vsxasryq\",\"amount\":\"20\"}],\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
