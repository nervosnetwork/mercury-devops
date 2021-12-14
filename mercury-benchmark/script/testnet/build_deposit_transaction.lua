wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_dao_deposit_transaction\",\"params\":[{\"from\":{\"items\":[{\"type\":\"Address\",\"value\":\"ckt1qzda0cr08m85hc8jlnfp3zer7xulejywt49kt2rr0vthywaa50xwsqfqyerlanzmnkxtmd9ww9n7gr66k8jt4tclm9jnk\"}],\"source\":\"Free\"},\"amount\":30000000000,\"fee_rate\":1000}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end