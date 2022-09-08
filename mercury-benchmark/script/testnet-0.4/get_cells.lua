wrk.method = "POST"
wrk.body   = "{\"id\":2,\"jsonrpc\":\"2.0\",\"method\":\"get_cells\",\"params\":[{\"script\":{\"code_hash\":\"0x9bd7e06f3ecf4be0f2fcd2188b23f1b9fcc88e5d4b65a8637b17723bbda3cce8\",\"hash_type\":\"type\",\"args\":\"0x87c79fa9f016362e17c1c52e800e4efbb5919802\"},\"script_type\":\"lock\"},\"asc\",\"0x64\"]}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
