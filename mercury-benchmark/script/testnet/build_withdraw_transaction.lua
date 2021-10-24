wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_withdraw_transaction\",\"params\":[{\"from\":{\"Address\":\"ckt1qyqzqfj8lmx9h8vvhk62uut8us844v0yh2hsnqvvgc\"},\"pay_fee\":\"ckt1qyq27z6pccncqlaamnh8ttapwn260egnt67ss2cwvz\",\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end