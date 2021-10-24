wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"get_transaction_info\",\"params\":[\"0x3e7772679760f8e084ad8f46b1605b75cac61e0c265746533599b0747c7b5db9\"],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end