wrk.method = "POST"
wrk.body   = "{\"jsonrpc\":\"2.0\",\"method\":\"build_dao_deposit_transaction\",\"params\":[{\"from\":[{\"type\":\"Address\",\"value\":\"ckt1qzda0cr08m85hc8jlnfp3zer7xulejywt49kt2rr0vthywaa50xwsqfqyerlanzmnkxtmd9ww9n7gr66k8jt4tclm9jnk\"}],\"amount\":\"0x6fc23ac00\",\"fee_rate\":\"0x3e8\"}],\"id\":100}"
wrk.headers["Content-Type"] = "application/json"

function response(status, headers, body)
    if (string.find(body, '"error"')) then
        print('error, resp: ', body)
        wrk.thread:stop()
    end
end
