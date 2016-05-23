package edu.hzuapps.androidworks.homeworks.net1314080903122.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/20.
 */
public class Net1314080903122_FestivalLab {

    public static  Net1314080903122_FestivalLab mInstance;
    private List<Net1314080903122_Festival> mFestivals = new ArrayList<Net1314080903122_Festival>();
    private List<Net1314080903122_Msg> mMsgs = new ArrayList<Net1314080903122_Msg>();


    private Net1314080903122_FestivalLab(){
        mFestivals.add(new Net1314080903122_Festival(1,"国庆节"));
        mFestivals.add(new Net1314080903122_Festival(1,"中秋节"));
        mFestivals.add(new Net1314080903122_Festival(1,"元旦"));
        mFestivals.add(new Net1314080903122_Festival(1,"春节"));
        mFestivals.add(new Net1314080903122_Festival(1,"端午节"));
        mFestivals.add(new Net1314080903122_Festival(1,"七夕节"));
        mFestivals.add(new Net1314080903122_Festival(1,"圣诞节"));
        mFestivals.add(new Net1314080903122_Festival(1,"儿童节"));

        mMsgs.add(new Net1314080903122_Msg(1,1,"(ﾉ≧ڡ≦)秋高气爽贺华诞，金秋十月度国庆，神州大地花似锦，国强民盛家繁荣，歌舞升平七天乐，吉祥如意事事顺！祝你国庆快乐，心想事成！"));
        mMsgs.add(new Net1314080903122_Msg(2,1,"٩(●˙ε˙●)۶无论天南与海北，不论相聚与离别，在中秋佳节，来！把酒话佳节！举杯邀明月！！ "));
        mMsgs.add(new Net1314080903122_Msg(3,1,"o(*////▽////*)o一丝真诚，胜过千两黄金；一丝温暖，能抵万里寒霜，一声问候，送来温馨甜蜜；一条短信，捎去我万般心意！愿我的朋友兔年元旦万事如意！"));
        mMsgs.add(new Net1314080903122_Msg(4,1,"(ฅ´ω`ฅ)相聚的日子都只为酝一杯浓酒，酿流动相思，在新年的鞭炮声中凝视你如此迷人的面庞，只想对你说：爱你一万年！"));
        mMsgs.add(new Net1314080903122_Msg(5,1,"٩۹(๑•̀ω•́ ๑)۶端午节，吃粽子，甜枣儿，颗颗红，糯米儿，粒粒晶，闻一闻，溢芳香，吃一口，情意长，忆先驱，送健康，祝大家，幸福路，万年长。"));
        mMsgs.add(new Net1314080903122_Msg(6,1,"(๑•̀ㅁ•́ฅ)爱是牛郎织女痴情的等待，银河两畔遥遥相望不离不弃；爱是你我真情的相守，彼此关怀执子之手与子偕老！七夕到来之前，想对你浪漫的说三个字：我爱你！"));
        mMsgs.add(new Net1314080903122_Msg(7,1,"o(〃｀Θ´〃)♂圣诞之夜，请把你的愿望袜子放在床边，我在一定会让你梦想成真，收到我那份真挚的祝福。"));
        mMsgs.add(new Net1314080903122_Msg(8,1,"φ(￣∇￣o)你的生命刚刚翻开了第一页，愿初升的太阳照耀你诗一般美丽的岁月。明天属于你们。"));
        mMsgs.add(new Net1314080903122_Msg(9,1,"✧٩(ˊωˋ*)و✧"));
        mMsgs.add(new Net1314080903122_Msg(10,1,"(*˘︶˘*).。.:*♡"));
        mMsgs.add(new Net1314080903122_Msg(11,1,"(っ╥╯﹏╰╥c)"));

    }

    public List<Net1314080903122_Msg> getmMsgsByFestivalId(int fesId){
        List<Net1314080903122_Msg> msgs = new ArrayList<Net1314080903122_Msg>();
        for (Net1314080903122_Msg msg : mMsgs){
            if(msg.getFestivalId() == fesId){
                msgs.add(msg);
            }
        }
        return msgs;
    }


    public Net1314080903122_Msg getMsgByMsgId(int id)
    {
        for (Net1314080903122_Msg msg : mMsgs)
        {
            if (msg.getId() == id)
                return msg;
        }
        return null;
    }


    public List<Net1314080903122_Festival> getmFestivals()
    {
        return new ArrayList<Net1314080903122_Festival>(mFestivals);
    }



    public Net1314080903122_Festival getFestivalById(int fesId)
    {
        for (Net1314080903122_Festival festival:mFestivals)
        {
            if (festival.getId() == fesId)
                return festival;
        }
        return null;
    }


    public static Net1314080903122_FestivalLab getInstance()
    {
        if (mInstance == null)
        {
            synchronized (Net1314080903122_FestivalLab.class)
            {
                if (mInstance == null)
                    mInstance = new Net1314080903122_FestivalLab();
            }
        }
        return mInstance;
    }




}
