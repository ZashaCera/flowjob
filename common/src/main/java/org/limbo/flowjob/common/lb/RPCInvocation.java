package org.limbo.flowjob.common.lb;

import java.util.Collections;
import java.util.Map;

/**
 * @author Brozen
 * @since 2022-12-14
 */
public class RPCInvocation implements Invocation {

    /**
     * 调用接口的 PATH
     */
    private final String path;


    public RPCInvocation(String path) {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getInvokeTargetId() {
        return this.path;
    }


    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Map<String, String> getLBParameters() {
        return Collections.emptyMap();
    }

}
