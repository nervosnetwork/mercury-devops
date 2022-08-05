wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_dao_withdraw_transaction\",\"params\":[{\"from\":[{\"type\":\"OutPoint\",\"value\":{\"tx_hash\":\"0x1b9757e95346d4782767c579f1d1131ead18043154229762911f82b75119f1d6\",\"index\":\"0x0\"}},{\"type\":\"Address\",\"value\":\"ckt1qyqr79tnk3pp34xp92gerxjc4p3mus2690psf0dd70\"}],\"fee_rate\":null}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
