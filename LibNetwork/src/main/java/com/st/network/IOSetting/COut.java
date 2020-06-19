package com.st.network.IOSetting;

/**
 * 给plc发指令
 */
public class COut extends BaseOut{

    private int mIndex;
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    @Override
    public String toString() {
        return "COut{" +
                "ip='" + getIP() + '\'' +
                "prot='" + getPort() + '\'' +
                "command='" + command + '\'' +
                '}';
    }
}
