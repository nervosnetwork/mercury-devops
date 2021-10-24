wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"get_spent_transaction\",\"params\":[{\"outpoint\":{\"tx_hash\":\"0xf004b16641c2a7418e075250e957bdc31234cf2ff9fed236faf17d25f79222f9\",\"index\":\"0x0\"},\"structure_type\":\"DoubleEntry\"}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end