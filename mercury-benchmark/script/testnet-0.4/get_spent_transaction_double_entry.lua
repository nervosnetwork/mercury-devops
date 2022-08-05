wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"get_spent_transaction\",\"params\":[{\"outpoint\":{\"tx_hash\":\"0xb2e952a30656b68044e1d5eed69f1967347248967785449260e3942443cbeece\",\"index\":\"0x1\"},\"structure_type\":\"DoubleEntry\"}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end