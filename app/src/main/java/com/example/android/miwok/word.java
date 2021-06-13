package com.example.android.miwok;

public class word {
    private String miwokTranslation;
    private String defaultTranslation;
    private int mediaRes;
    private int imageres=NOID;
    private static final int NOID=-1;
    public word(String defalutWord,String miwokWord)
    {
        miwokTranslation=miwokWord;
        defaultTranslation=defalutWord;
    }
    public word(String defalutWord,String miwokWord,int imageres)
    {
        miwokTranslation=miwokWord;
        defaultTranslation=defalutWord;
        this.imageres=imageres;
    }

    public word(String defalutWord,String miwokWord,int imageres,int mediaRes)
    {
        miwokTranslation=miwokWord;
        defaultTranslation=defalutWord;
        this.imageres=imageres;
        this.mediaRes=mediaRes;
    }
    public String getMiwokTranslation()
    {
        return miwokTranslation;
    }
    public String getDefaultTranslation()
    {
        return defaultTranslation;
    }
    public int getImageResource(){ return imageres;}
    public int getMediaResource(){ return mediaRes;}
    public boolean hasId()
    {
        return imageres!=NOID;
    }


}
