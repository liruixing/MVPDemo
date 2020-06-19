package com.st.network.IOSetting;

public class CIn {

    private CInType _InType;
    private int _InPort;
    private int _Station;
    private String _Rfids;
    private int bytesCommand;
    private int portCount;
    private String bytesCommandStr;

    public CIn(CInType _InType, int _InPort, int _Station, String _Rfids) {
        this._InType = _InType;
        this._InPort = _InPort;
        this._Station = _Station;
        this._Rfids = _Rfids;
    }

    @Override
    public String toString() {
        return "CIn{" +
                "_InType=" + _InType +
                ", _InPort=" + _InPort +
                ", _Station=" + _Station +
                ", _Rfids='" + _Rfids + '\'' +
                ", bytesCommand=" + bytesCommand +
                ", portCount=" + portCount +
                ", bytesCommandStr='" + bytesCommandStr + '\'' +
                '}';
    }

    public int getPortCount() {
        return portCount;
    }

    public void setPortCount(int portCount) {
        this.portCount = portCount;
    }

    public String getBytesCommandStr() {
        return bytesCommandStr;
    }

    public void setBytesCommandStr(String bytesCommandStr) {
        this.bytesCommandStr = bytesCommandStr;
    }

    public int getBytesCommand() {
        return bytesCommand;
    }

    public void setBytesCommand(int bytesCommand) {
        this.bytesCommand = bytesCommand;
    }

    public CInType get_InType() {
        return _InType;
    }

    public void set_InType(CInType _InType) {
        this._InType = _InType;
    }

    public int get_InPort() {
        return _InPort;
    }

    public void set_InPort(int _InPort) {
        this._InPort = _InPort;
    }

    public int get_Station() {
        return _Station;
    }

    public void set_Station(int _Station) {
        this._Station = _Station;
    }

    public String get_Rfids() {
        return _Rfids;
    }

    public void set_Rfids(String _Rfids) {
        this._Rfids = _Rfids;
    }

}
