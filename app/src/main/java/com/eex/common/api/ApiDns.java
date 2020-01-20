package com.eex.common.api;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Dns;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: OverThrow
 * @Package: com.overthrow.common.api
 * @ClassName: ApiDns
 * @Description: java类作用描述
 * @Author: 胡成军
 * @CreateDate: 2019/9/6 10:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/9/6 10:58
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ApiDns implements Dns {

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        if (hostname == null) {
            throw new UnknownHostException("hostname == null");
        } else {
            try {
                List<InetAddress> mInetAddressesList = new ArrayList<>();
                InetAddress[] mInetAddresses = InetAddress.getAllByName(hostname);
                for (InetAddress address : mInetAddresses) {
                    if (address instanceof Inet4Address) {
                        mInetAddressesList.add(0, address);
                    } else {
                        mInetAddressesList.add(address);
                    }
                }
                return mInetAddressesList;
            } catch (NullPointerException var4) {
                UnknownHostException unknownHostException = new UnknownHostException("Broken system behaviour");
                unknownHostException.initCause(var4);
                throw unknownHostException;
            }
        }
    }
}
