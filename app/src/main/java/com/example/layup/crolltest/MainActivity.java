package com.example.layup.crolltest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    int b,c;
    ProgressBar prograssBar;
    AlertDialog.Builder dialog;

    Button reload; //reload버튼


    Document doc, pathdoc = null;

    Elements contents, imgUrlsConvert;
    Elements links, imgUrls;


    String Top10;//결과를 저장할 문자열변수
    String Top11; //이미지 배열저장할 문자열 변수
    String Top12;




    Handler handler = new Handler(); //import android.os.Handler; handler가 두가지 있다.   안드로이드 메인 스레드와 작업 스레드간에 통신을 위해서 필요하다.  작업 스레드는 메인 스레드의 UI를 변경할수 없기 때문.
    Handler handler2;

    String[] urlStr = new String[20]; // 이미지 링크 url 주소를 넣을 배열



    Integer txId[] = {
            R.id.textView1,
            R.id.textView2,
            R.id.textView3,
            R.id.textView4,
            R.id.textView5,
            R.id.textView6,
            R.id.textView7,
            R.id.textView8,
            R.id.textView9,
            R.id.textView10,
            R.id.textView11,
            R.id.textView12,
            R.id.textView13,
            R.id.textView14,
            R.id.textView15,
            R.id.textView16,
            R.id.textView17,
            R.id.textView18,
            R.id.textView19,
            R.id.textView20
    };


    Integer imageId[] = {
            R.id.imageView1,
            R.id.imageView2,
            R.id.imageView3,
            R.id.imageView4,
            R.id.imageView5,
            R.id.imageView6,
            R.id.imageView7,
            R.id.imageView8,
            R.id.imageView9,
            R.id.imageView10,
            R.id.imageView11,
            R.id.imageView12,
            R.id.imageView13,
            R.id.imageView14,
            R.id.imageView15,
            R.id.imageView16,
            R.id.imageView17,
            R.id.imageView18,
            R.id.imageView19,
            R.id.imageView20};

    TextView tx[] = new TextView[20]; //동적으로 생성할 TextView  ('findViewByid 하기 위해 선언했다.)
    ImageView image[] = new ImageView[20];  // 동적으로 생성할 ImageView  ('findViewByid 하기 위해 선언했다.)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prograssBar = (ProgressBar)findViewById(R.id.ProgressBar01);
        dialog = new AlertDialog.Builder(this);


        handler2 = new Handler() {
            public void handleMessage(Message msg) {
                reload.setText(msg.arg1+"$");
                if (msg.arg1 == 100) {
                    dialog.setCancelable(false);
                    dialog.setMessage("완료");
                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            prograssBar.setProgress(0);
                        }
                    });
                    dialog.show();
                }
            }
        };


        reload = (Button) findViewById(R.id.reload); //버튼  - 아이디찾기


        //텍스트본문과 이미지 아이디배열로 찾기
        for (int i = 0; i < txId.length; i++) {
            tx[i] = (TextView) findViewById(txId[i]);
            image[i] = (ImageView) findViewById(imageId[i]);
        }




                new AsyncTask() {//AsyncTask객체 생성


                    @Override
                    protected Object doInBackground(Object[] params) {
                        try {
                            //본문 파트
                            doc = Jsoup.connect("http://www.all-con.co.kr/uni_contest").get(); //naver페이지를 불러옴
                                  contents = doc.select("td.title.white");//  //td 태그중 class 값이 title white 인 것을 선택.

                                  //links = doc.select("td.title.white > a"); //td 태그중 class 값이 title white 이며 하위 태그가 a 인것을 선택.
                                  //imgUrls = doc.select("td.extravars.white > img[src$=.gif]"); // td 태그중 class 값이 extravars white 이며 하위 img 태그의 src 의 내용이 .gif 인것만 선택.

                            Log.i("TAG","500"+ contents + '\n');


                            //이미지 파트
                            /**상대경로를 절대경로로 바꾸기 [ 위는 안되고 이렇게 해야만 작동하네.. ] 여기 로그찍으면 계속해서 실시간 갱신됨 */
                            pathdoc = Jsoup.parse(
                                    new URL("http://www.all-con.co.kr/uni_contest").openConnection().getInputStream(),
                                    "UTF-8",
                                    "http://www.all-con.co.kr/");
                                    imgUrlsConvert = pathdoc.select("td.extravars.white > img[src$=.gif]");






                            for (Element elem : imgUrlsConvert) {  /** [향상된 for 문]  ->    타입변수 : 배열  */
                                if (!elem.attr("src").equals(elem.attr("abs:src"))) {
                                    elem.attr("src", elem.attr("abs:src"));
                                    Log.i("TAG", "누가먼저22" + Top11 + '\n');
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }




                        Top10 = "";
                        Top11 = "";
                        Top12 ="";

                        int cnt = 0;//앞에 숫자를 카운트하기위한 변수
                        int cnt2 = 0;



                        for (Element element : contents) {
                            ++cnt;
                            Top10 += "#" + cnt + ". " + element.text() + "\n";
                                    //+ "변환하기 전 [imgUrls] 경로: " + imgUrls.attr("src") + "\n"  //src 속성만 출력함.
                                    //+ "변환 후 [imgUrlsConvert] 경로: " + imgUrlsConvert.attr("src") + "\n" + "\n";  //src 속성에서 절대경로로 변환하여 출력함
                                    //+ element.getElementsByAttribute("href").attr("href") + "ㅁㄴㅇㅁㄴㅇㅁㄴㅇ"+ "\n";  //본문의 링크 크롤링




                            Top12 += "#" + element.getElementsByAttribute("href").attr("href");  //Top10 의 [본문 크롤링 내용] 엘리먼트에서 'href' 부분만 추출하는 함수.

                            Log.i("TAG", "[처음 문자 # 제거하지않은] [본문이 참조하는 링크]" + Top12 + '\n');

                            if (cnt > 20)//20위까지 파싱하므로
                                break;
                        }


                        for (Element element2 : imgUrlsConvert) {
                            cnt2++;
                            Top11 += "@" + element2.attr("src");
                            Log.i("TAG", "[처음 문자 # 제거하지않은] 상태이미지 내용 Top11 : "+ Top11 + '\n');

                            if (cnt2 > 20)
                                break;

                        }
                        return null;
                    }


                    @Override
                    protected void onPostExecute(Object o) { //doInBackground() 가 실행되기 이전에 호출되는 함수로 여기에서는ProgressDialog 객체를 생성한다. 그리고 show 함수를 통해 다이얼로그를 화면에 띄운다.
                        super.onPostExecute(o);

                        reload.setText("게시글 수:" + contents.size()); //Button 객체의 텍스트수정





                        /** '#' 으로 구분한 본문크롤링 넣기 */
                        Top10 = Top10.replaceFirst("#","");  // 위에서 append() 함수사용시 #가 앞에 붙었다.  @로 구분된 내용에서 반드시 맨 앞 '@'를 제거해주어야 split() 함수가 제대로 구분된다.      [[[ 설명 : replaceFirst("찿을문자열" ,"바꿀문자열")  ]]]
                        Log.i("Tag","[처음 문자 # 제거한] [본문 크롤링 내용] Top10 : " +Top10);


                        Top12 = Top12.replaceFirst("#","");  // 위에서 append() 함수사용시 #가 앞에 붙었다.  @로 구분된 내용에서 반드시 맨 앞 '@'를 제거해주어야 split() 함수가 제대로 구분된다.
                        Log.i("Tag","[처음 문자 # 제거한] [본문이 참조하는 링크] Top12 : " +Top12);




                        String[] Txaaa = Top10.split("#"); // [본문 크롤링 내용] 배열로 삽입
                        final String[] Lnkaaa = Top12.split("#"); //  [본문이 참조하는 링크] 배열로 삽입





                        for (int i = 0; i < txId.length; i++) {
                            final int indexTx;
                            indexTx = i;

                            tx[indexTx].setText(Txaaa[indexTx]);  // [본문 크롤링 내용] 0~19 까지 출력

                            tx[indexTx].setOnClickListener(new View.OnClickListener() {  // [본문이 참조하는 링크] 0~19 까지 onClickListener 등록
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Lnkaaa[indexTx]));
                                    startActivity(intent);
                                }
                            });















                        /* *//** '#' 으로 구분한 본문내용을 참조하는 링크 넣기 및 Intent *//*
                         Top12 = Top12.replaceFirst("#","");  // 위에서 append() 함수사용시 #가 앞에 붙었다.  @로 구분된 내용에서 반드시 맨 앞 '@'를 제거해주어야 split() 함수가 제대로 구분된다.
                         Log.i("Tag","이놈" +Top12);
                         final String[] Lnkaaa = Top12.split("#");


                         for (int j = 0; j < txId.length; j++){
                             Log.d("TAG", "ppp" + Lnkaaa[j] +" AND" + Lnkaaa.length);

                             final int finalJ = j;
                             tx[j].setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Lnkaaa[finalJ]));
                                     startActivity(intent);
                                    }
                                });
                            }*/

                        }











                        /** '@' 으로 구분한 이미지링크 넣기 [최종 이미지 URL 스트링을 저장한 내용] */
                        Top11 = Top11.replaceFirst("@","");  // 위에서 append() 함수사용시 @가 앞에 붙었다.  @로 구분된 내용에서 반드시 맨 앞 '@'를 제거해주어야 split() 함수가 제대로 구분된다.
                        Log.i("Tag","이것" +Top11);
                        String[] Imaaa = Top11.split("@"); // 위에서 Top11에 문자열에서 링크마다 구분하기위해 '@' 를 앞에 추가시켰다. 그 것을 쪼갠 최종판이 "Imaaap[]" 이다


                        Log.i("Tag","이것이" +Imaaa.length);


                        for (int i = 0; i < Imaaa.length; i++) {  //22개라고 직힘
                            Log.i("Tag","이것이 로" +Imaaa[i]);

                            urlStr[i] = Imaaa[i];  //이 urlStr 라는 20 주소를 배열로 가진 최종변수
                            //Log.i("Tag","이것이 로그" +urlStr[i]);

                            for (int j = 0; j < urlStr.length; j++) {  //22개라고 직힘
                                Log.i("Tag","이것이 로그" +urlStr[j]);


                            }
                        }





                        /** 비트맵 HTTP 통신 Thread 클래스 객체 생성동작 */
                        ConnectThread thread = new ConnectThread(urlStr);  //추출 된 urlStr 파라미터를 가진 Thread 를 생성하고 시작한다. 역할은 Http 통신 비트맵 변환.
                        Log.i("Tag","이것이 로그444444" +urlStr);

                        thread.start();
                    }
                }.execute();



    }//절대영역




    class ConnectThread extends Thread {
        String[] urlStr;//이미지 파일 경로
        String[] bitStr = new String[20];
        int c;

        public ConnectThread(String[] inStr) {   //애는 무조건 한개만 있어야 하는 듯. x 수정 ->  아니다 생성자는 여러개 있을 수 있다. 그리고 클래스이름과 같게 만들고 리턴이 없다는 것이 특징이다.
            urlStr = inStr;

            //Log.i("TAG","찾아보자2"+ urlStr[0] + '\n');
            Log.i("TAG","찾아보자2"+ urlStr[1] + '\n');
            Log.i("TAG","찾아보자200"+ bitStr[19] + '\n');
        }


            public void run() {  //상속받은 오버로딩메소드
                try {



                final Bitmap[] output = request(bitStr);      /** 맨 아래의 HTTP 통신한 비트맵 데이터 받아오기 */
                Log.i("TAG8001", bitStr[1]);
                Log.i("TAG8002", bitStr[19]);






                //handler 를 이용하여 메인 스레드의 ui인 imgView 수정
                handler2.post(new Runnable() {
                    public void run() {


                        for (int i = 0; i < imageId.length; i++) {   //imageId 는 findViewById 하여 Integer로 고유넘버 객체화시킨 배열. 수는 절대값
                            final int index;
                            index = i;
                            b = i+81;
                            c=b;
                            image[index].setImageBitmap(output[i]); /**    이곳에 이미지 데이터를 차곡차곡 배열로 넣도록 setImageBitmap 작업을 작성해봄.. */
                                                                /** request 객체 output을받아와야하지만 request 매개변수가 urlStr인식을 하지않음.*/

                                                                Log.i("TAG7000", "#########################################################" +output[3] + '\n');  //이미지뷰의 개수 2~22 출력 output으로 바꾸면 나오지않음 나오게해야한다.

                             Message msg = handler2.obtainMessage();
                             msg.arg1 = c;
                             handler2.sendMessage(msg);
                            prograssBar.setProgress(c);

                             Log.i("TAG","고고"+ msg.arg1 + '\n');
                                try {
                                     Thread.sleep(1000);
                                      } catch (InterruptedException e) {
                                        e.printStackTrace();
                                      }





                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) image[index].getLayoutParams(); // 이미지 크기조절
                            layoutParams.width = 300; // 이미지 크기조절
                            layoutParams.height = 150; // 이미지 크기조절
                            image[index].setLayoutParams(layoutParams); // 이미지 크기조절
                            image[index].getBaselineAlignBottom(); // 이미지 크기조절
                        }
                    }//----Runnable(Run) 마지막
                });//----Runnable 마지막


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } //----Run 마지막







        private Bitmap[] request(String[] bitStr) {
            for(int i=0; i<urlStr.length; i++){
                bitStr[i] = urlStr[i];
                Log.i("TAG","dddddddddddddddddddd"+ bitStr[i] + '\n');
            }


            Bitmap[] bm = new Bitmap[bitStr.length];
            Log.i("TAG","초초초"+ bitStr.length + '\n');



            try {



                URL urls = new URL(bitStr[0]); URL urls2 = new URL(bitStr[1]);
                URL urls3 = new URL(bitStr[2]); URL urls4 = new URL(bitStr[3]);
                URL urls5 = new URL(bitStr[4]); URL urls6 = new URL(bitStr[5]);
                URL urls7 = new URL(bitStr[6]); URL urls8 = new URL(bitStr[7]);
                URL urls9 = new URL(bitStr[8]); URL urls10 = new URL(bitStr[9]);

                URL urls11 = new URL(bitStr[10]); URL urls12 = new URL(bitStr[11]);
                URL urls13 = new URL(bitStr[12]); URL urls14 = new URL(bitStr[13]);
                URL urls15 = new URL(bitStr[14]); URL urls16 = new URL(bitStr[15]);
                URL urls17 = new URL(bitStr[16]); URL urls18 = new URL(bitStr[17]);
                URL urls19 = new URL(bitStr[18]); URL urls20 = new URL(bitStr[19]);
                //Log.d("TAG222", String.valueOf(urls));
                //Log.d("TAG3333", String.valueOf(urls2));


                //크롤링할 공모전 사이트 뼈대 페이지를 객체화 시킴. (conn)
                HttpURLConnection conn = (HttpURLConnection) urls.openConnection(); HttpURLConnection conn2 = (HttpURLConnection) urls2.openConnection();
                HttpURLConnection conn3 = (HttpURLConnection) urls3.openConnection(); HttpURLConnection conn4 = (HttpURLConnection) urls4.openConnection();
                HttpURLConnection conn5 = (HttpURLConnection) urls5.openConnection(); HttpURLConnection conn6 = (HttpURLConnection) urls6.openConnection();
                HttpURLConnection conn7 = (HttpURLConnection) urls7.openConnection(); HttpURLConnection conn8 = (HttpURLConnection) urls8.openConnection();
                HttpURLConnection conn9 = (HttpURLConnection) urls9.openConnection(); HttpURLConnection conn10 = (HttpURLConnection) urls10.openConnection();

                HttpURLConnection conn11 = (HttpURLConnection) urls11.openConnection(); HttpURLConnection conn12 = (HttpURLConnection) urls12.openConnection();
                HttpURLConnection conn13 = (HttpURLConnection) urls13.openConnection(); HttpURLConnection conn14 = (HttpURLConnection) urls14.openConnection();
                HttpURLConnection conn15 = (HttpURLConnection) urls15.openConnection(); HttpURLConnection conn16 = (HttpURLConnection) urls16.openConnection();
                HttpURLConnection conn17 = (HttpURLConnection) urls17.openConnection(); HttpURLConnection conn18 = (HttpURLConnection) urls18.openConnection();
                HttpURLConnection conn19 = (HttpURLConnection) urls19.openConnection(); HttpURLConnection conn20 = (HttpURLConnection) urls20.openConnection();

                if (conn != null) {

                    /**비트맵 데이터 받아오기 */
                    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream()); BufferedInputStream bis2 = new BufferedInputStream(conn2.getInputStream());
                    BufferedInputStream bis3 = new BufferedInputStream(conn3.getInputStream()); BufferedInputStream bis4 = new BufferedInputStream(conn4.getInputStream());
                    BufferedInputStream bis5 = new BufferedInputStream(conn5.getInputStream()); BufferedInputStream bis6 = new BufferedInputStream(conn6.getInputStream());
                    BufferedInputStream bis7 = new BufferedInputStream(conn7.getInputStream()); BufferedInputStream bis8 = new BufferedInputStream(conn8.getInputStream());
                    BufferedInputStream bis9 = new BufferedInputStream(conn9.getInputStream()); BufferedInputStream bis10 = new BufferedInputStream(conn10.getInputStream());

                    BufferedInputStream bis11 = new BufferedInputStream(conn11.getInputStream()); BufferedInputStream bis12 = new BufferedInputStream(conn12.getInputStream());
                    BufferedInputStream bis13 = new BufferedInputStream(conn13.getInputStream()); BufferedInputStream bis14 = new BufferedInputStream(conn14.getInputStream());
                    BufferedInputStream bis15 = new BufferedInputStream(conn15.getInputStream()); BufferedInputStream bis16 = new BufferedInputStream(conn16.getInputStream());
                    BufferedInputStream bis17 = new BufferedInputStream(conn17.getInputStream()); BufferedInputStream bis18 = new BufferedInputStream(conn18.getInputStream());
                    BufferedInputStream bis19 = new BufferedInputStream(conn19.getInputStream()); BufferedInputStream bis20 = new BufferedInputStream(conn20.getInputStream());

                    Log.d("TAG77","sd1"+bis);
                    Log.d("TAG77","sd2"+bis2);
                    Log.d("TAG77","sd3"+bis13);


                    /**  받아온 데이터를 비트맵 형식으로 변환  */
                    /*BitmapFactory.decodeFile() 메소드는 파일을 그대로 읽어 옵니다.
                      내부적으로는 파일 경로를 가지고 FileInputStream을 만들어서 decodeStream을 합니다.
                      그냥 파일 경로만 쓰면 다 해주는게 편리 한 것이죠.*/

                    for (int i=0; i<bm.length; i++)
                    bm[0] = BitmapFactory.decodeStream(bis); bis.close(); conn.disconnect();
                    bm[1] = BitmapFactory.decodeStream(bis2);  bis2.close(); conn2.disconnect();
                    bm[2] = BitmapFactory.decodeStream(bis3);  bis3.close(); conn3.disconnect();
                    bm[3] = BitmapFactory.decodeStream(bis4);  bis4.close(); conn4.disconnect();
                    bm[4] = BitmapFactory.decodeStream(bis5);  bis5.close(); conn5.disconnect();
                    bm[5] = BitmapFactory.decodeStream(bis6);  bis6.close(); conn6.disconnect();
                    bm[6] = BitmapFactory.decodeStream(bis7);  bis7.close(); conn7.disconnect();
                    bm[7] = BitmapFactory.decodeStream(bis8);  bis8.close(); conn8.disconnect();
                    bm[8] = BitmapFactory.decodeStream(bis9);  bis9.close(); conn9.disconnect();
                    bm[9] = BitmapFactory.decodeStream(bis10); bis10.close(); conn10.disconnect();
                    bm[10] = BitmapFactory.decodeStream(bis11);  bis11.close(); conn11.disconnect();
                    bm[11] = BitmapFactory.decodeStream(bis12);  bis12.close(); conn12.disconnect();
                    bm[12] = BitmapFactory.decodeStream(bis13);  bis13.close(); conn13.disconnect();
                    bm[13] = BitmapFactory.decodeStream(bis14);  bis14.close(); conn14.disconnect();
                    bm[14] = BitmapFactory.decodeStream(bis15);  bis15.close(); conn15.disconnect();
                    bm[15] = BitmapFactory.decodeStream(bis16);  bis16.close(); conn16.disconnect();
                    bm[16] = BitmapFactory.decodeStream(bis17);  bis17.close(); conn17.disconnect();
                    bm[17] = BitmapFactory.decodeStream(bis18);  bis18.close(); conn18.disconnect();
                    bm[18] = BitmapFactory.decodeStream(bis19);  bis19.close(); conn19.disconnect();
                    bm[19] = BitmapFactory.decodeStream(bis20);  bis20.close(); conn20.disconnect();

                    Log.i("TAG","로그로그로글"+ bm[1] + '\n');
                    Log.i("TAG","로그로그로글"+ bm[2] + '\n');
                    Log.i("TAG","로그로그로글"+ bm[10] + '\n');
                    Log.i("TAG","로그로그로글"+ bm[11] + '\n');
                    Log.i("TAG","로그로그로글"+ bm[19] + '\n');







                    // 21개의 텍스트 ID 배열
                    //URL[] urls = new URL[22]; 이건안되는걸로
                /*for(int j=0; j < urls.length; j++){
                    Log.i("Tag","로그는 도적이다"+urls[0]);
                    Log.i("Tag","로그는 도적이다"+urls[1]);
                    Log.i("Tag","로그는 도적이다"+urls[2]);
                }*/


                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return bm;
        }
        //.
    }












}

