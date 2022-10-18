package com.example.mypython.rmnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
class PrivacyData{

}
public class CallRMNet {

    public static void callRmnet() throws IOException, InterruptedException{
        String[] pythonArgs = new String[] {
                "C:\\apps\\Anaconda3\\envs\\pyProject\\python.exe",
                "C:\\Users\\Administrator\\PycharmProjects\\pyProject\\rmnetstarter.py",
                "--inference" ,
                "--weights=C:/Users/Administrator/PycharmProjects/RMNet/RMNet-DAVIS.pth",
                "--IMG_FILE_PATH=/root/zcd/Desktop/Annotations_face/JPEGImages/480p/%s/%05d.png",
                "--ANNOTATION_FILE_PATH=/root/zcd/Desktop/Annotations_face/Annotations/480p/%s/%05d.png",
                "--OPTICAL_FLOW_FILE_PATH=/root/zcd/Desktop/output1/%s/%05d.flo",
                "--N_FRAMES_NAME=30:aa"
        };

        Process proc =Runtime.getRuntime().exec(pythonArgs);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gbk"));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        BufferedReader ein = new BufferedReader(new InputStreamReader(proc.getErrorStream(),"gbk"));
        String eline = null;
        while ((eline = ein.readLine()) != null) {
            System.out.println(eline);
        }
        ein.close();
        proc.waitFor();
    }
    public static void callFlo() throws IOException, InterruptedException{
//        --inference
//                --model
//                FlowNet2
//        --save_flow
//                --inference_dataset
//                ImagesFromFolder
//        --inference_dataset_root
//        C:/Users/Administrator/PycharmProjects/Annotations_face/JPEGImages/480p
//                --resume
//        C:/Users/Administrator/PycharmProjects/FlowNet2_checkpoint.pth.tar
//                --save
//        C:/Users/Administrator/PycharmProjects/Annotations_face/output

        String[] pythonArgs = new String[] {
                "C:\\apps\\Anaconda3\\envs\\pyProject\\python.exe",
                "C:\\Users\\Administrator\\PycharmProjects\\pyProject\\flownetstarter.py",
                "--inference" ,
                "--model=FlowNet2",
                "--inference_dataset=ImagesFromFolder",
                "--inference_dataset_root=C:/Users/Administrator/PycharmProjects/Annotations_face/JPEGImages/480p",
                "--resume=C:/Users/Administrator/PycharmProjects/FlowNet2_checkpoint.pth.tar",
                "--save=C:/Users/Administrator/PycharmProjects/Annotations_face/output"
        };

        Process proc =Runtime.getRuntime().exec(pythonArgs);
        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"gbk"));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        BufferedReader ein = new BufferedReader(new InputStreamReader(proc.getErrorStream(),"gbk"));
        String eline = null;
        while ((eline = ein.readLine()) != null) {
            System.out.println(eline);
        }
        ein.close();
        proc.waitFor();
    }



    public static void main(String[] args) throws IOException, InterruptedException {



    }
}
