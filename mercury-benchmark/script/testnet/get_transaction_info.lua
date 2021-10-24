wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"get_transaction_info\",\"params\":[\"0x4329e4c751c95384a51072d4cbc9911a101fd08fc32c687353d016bf38b8b22c\"],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end