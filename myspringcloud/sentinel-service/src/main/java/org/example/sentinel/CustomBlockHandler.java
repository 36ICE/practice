package org.example.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class CustomBlockHandler {



    public CommonResult handleException(BlockException exception){
        return new CommonResult("自定义限流信息",200);
    }


}
