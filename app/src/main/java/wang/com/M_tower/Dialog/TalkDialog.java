package wang.com.M_tower.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import wang.com.M_tower.GamePlayConstants;
import wang.com.M_tower.R;
import wang.com.M_tower.View.MapView;


public class TalkDialog extends AlertDialog {
    protected TalkDialog(Context context) {
        super(context);
    }

    protected TalkDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public TalkDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    private LinearLayout cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk);
        cnt = findViewById(R.id.cnt);
        cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void talkWithElder(int code){
        TextView talk = findViewById(R.id.talktext);
        ImageView imageView = findViewById(R.id.npc_image);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.older));
        switch (code){
            case 3:
                talk.setTextSize(16);
                talk.setText("：你好勇士，听说有一种神器，叫做屠龙匕首。");
                break;
            case 4:
                talk.setTextSize(15);
                talk.setText("：你每次升级需要的经验会逐渐增加，因此升级道具尽量等到后期再用");
                break;
            case 5:
                talk.setTextSize(15);
                talk.setText("：锄头是一个非常重要的东西，不到万不得已不要使用");
                break;
            case 6:
                talk.setTextSize(16);
                talk.setText("：你在商人那里买了东西，他会告诉你重要信息。");
                break;
            case 7:
                talk.setTextSize(16);
                talk.setText("：听说福袋里共有五种东西，勇士你慢慢体会吧！。");
                break;
        }
    }

    public void talkWithSpirit(int code){
        TextView talk = findViewById(R.id.talktext);
        ImageView imageView = findViewById(R.id.npc_image);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.spirit));
        switch (code){
            case 1:
                talk.setTextSize(16);
                talk.setText("：勇士留步，我有重要的事对你讲！");
                break;
            case 2:
                talk.setTextSize(15);
                talk.setText("：你是尼古拉斯·永强，这次来救公主，是为了获得赏金而来，然后拯救你的国家，我说的对吗？");
                break;
            case 3:
                talk.setTextSize(16);
                talk.setText("：因为我是无所不知的小仙子，废话少说！快去魔塔帮我寻找到十字架。");
                break;
            case 4:
                talk.setTextSize(16);
                talk.setText("：有了十字架，我就可以提升你的能力了，你懂吗？");
                break;
            case 5:
                talk.setTextSize(15);
                talk.setText("：你是这个世界上唯一能击败魔王的人。");
                break;
            case 6:
                talk.setTextSize(16);
                talk.setText("：废话少说，快去吧");
                break;
            case 7:
                talk.setTextSize(16);
                talk.setText("：加油吧，勇士！");
                break;
            case 8:
                talk.setTextSize(13);
                talk.setText("：你果然找到了十字架，我这就给你提升能力！上去吧！我会暗中帮助你的。");
                MapView.ShowCode = GamePlayConstants.GameValueConstants.NPC_MAIDEN;
                MapView.time = 0;
                break;
            case 10:
                talk.setTextSize(15);
                talk.setText("：勇士，你打败了魔王，救了公主，也拯救了我们精灵族。感谢你！你通关了！");
                break;
        }
    }

    public void talkWithHero(int code){
        TextView talk = findViewById(R.id.talktext);
        ImageView imageView = findViewById(R.id.npc_image);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.hero));
        switch (code){
            case 1:
                talk.setTextSize(16);
                talk.setText("：你是谁？干嘛挡住我的去路");
                break;
            case 2:
                talk.setTextSize(16);
                talk.setText("：你怎么知道？");
                break;
            case 3:
                talk.setTextSize(16);
                talk.setText("：凭什么啊?");
                break;
            case 4:
                talk.setTextSize(16);
                talk.setText("：真的吗？");
                break;
            case 5:
                talk.setTextSize(16);
                talk.setText("：。。。");
                break;
            case 6:
                talk.setTextSize(16);
                talk.setText("：行，你等着");
                break;
            case 7:
                talk.setTextSize(16);
                talk.setText("：莫慌，不就是两个小骷髅嘛。");
                break;
            case 8:
                talk.setTextSize(16);
                talk.setText("：我应该谢谢你才对！要不要和我一起上去。");
                break;
            case 9:
                talk.setTextSize(16);
                talk.setText("：不！可！能！");
                break;
            case 10:
                talk.setTextSize(16);
                talk.setText("：我并非为了她，而是为了苍生!");
                break;
        }
    }

    public void talkWithThief(int code) {
        TextView talk = findViewById(R.id.talktext);
        ImageView imageView = findViewById(R.id.npc_image);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.thief));
        switch (code) {
            case 1:
                talk.setTextSize(16);
                talk.setText("：救命啊！！前面的白骨精挡住了我的去路。");
                break;
            case 2:
                talk.setTextSize(16);
                talk.setText("：谢谢你救我，我是刚到这里的小偷，为了表示感谢，我送你几个刚偷到的宝物吧，好像是上下楼用的!");
                break;
            case 3:
                talk.setTextSize(16);
                talk.setText("：不用了，我再去这层搜搜有什么宝物，我先撤了。拜拜");
                break;
            case 4:
                talk.setTextSize(16);
                talk.setText("：你终于来了，这有一只变异了的丧尸，我把墙挖开了，咱们快跑吧！");
                break;
        }
    }

    public void talkWithTrader(int code) {
        TextView talk = findViewById(R.id.talktext);
        ImageView imageView = findViewById(R.id.npc_image);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.trader));
        switch (code) {
            case 6:
                talk.setTextSize(16);
                talk.setText("：这个魔塔一共有30层，越往上越人迹罕至，但是我隐约听到30层楼以上还有动静");
                break;
            case 7:
                talk.setTextSize(16);
                talk.setText("：你最好选择提升防御力，当你攻击力小于别人防御力时再买攻击力");
                break;
            case 12:
                talk.setTextSize(16);
                talk.setText("：找到像雪花一样的冰冻徽章，你就可以不怕火海了");
                break;
        }
    }

    public void talkWithMowang1(int code) {
        TextView talk1 = findViewById(R.id.talktext);
        ImageView imageView1 = findViewById(R.id.npc_image);
        imageView1.setImageDrawable(imageView1.getResources().getDrawable(R.drawable.mowang));
        switch (code) {
            case 1:
                talk1.setTextSize(16);
                talk1.setText("：你果然是世间少有的天才，做我的手下吧！我们俩平分这世界，岂不美哉？");
                break;
            case 2:
                talk1.setTextSize(16);
                talk1.setText("：呵呵呵呵，你难道要为了这女人而放弃去当地球的统治者？那我现在就杀死她。");
                break;
            case 3:
                talk1.setTextSize(16);
                talk1.setText("：废物，我看你就是为了你自己吧。我现在就治治你的脑袋");
                break;
            case 4:
                talk1.setTextSize(16);
                talk1.setText("：这就是天命吗？！唉");
                break;
        }
    }

    public void talkWithMowang(){
        TextView talk = findViewById(R.id.talktext);
        ImageView imageView = findViewById(R.id.npc_image);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.mowang1));
        talk.setTextSize(16);
        talk.setText("：能来到这里，我很欣赏你！来来来，击败我，然后往上走。让我看看你的能耐！");
    }

    public void talkWithGirl(){
        TextView talk = findViewById(R.id.talktext);
        ImageView imageView = findViewById(R.id.npc_image);
        imageView.setImageDrawable(imageView.getResources().getDrawable(R.drawable.girl));
        talk.setTextSize(16);
        talk.setText("：谢谢你救了我，我们一起出去吧！");
    }
}
