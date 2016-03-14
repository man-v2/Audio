import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-3-14
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
public class AudioServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String srcPath = request.getServletContext().getRealPath("/audios");
        File file = new File(srcPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + System.currentTimeMillis();
        String txt = request.getParameter("txt");
        StringBuffer param = new StringBuffer();
        param.append("appid=56df8184");
        SpeechUtility.createUtility(param.toString());
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
        mTts.setParameter("speed", "50");
        mTts.setParameter("voice_name", "xiaoyan");
        mTts.setParameter("engine_type", "cloud");
        mTts.setParameter("pitch", "50");
        mTts.setParameter("background_sound", "0");
        mTts.setParameter("tts_audio_path", srcPath + "/" + fileName + ".pcm");
        mTts.startSpeaking(txt, null);
        while (true) {
            if (!mTts.isSpeaking()) {
                File audioFile = new File(srcPath + "/" + fileName + ".pcm");
                if (audioFile.exists()) {
                    ChangePcmForWav changePcmForWav = new ChangePcmForWav();
                    try {
                        changePcmForWav.convertAudioFiles(srcPath + "/" + fileName + ".pcm", srcPath + "/" + fileName + ".mp3");
                        audioFile.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        response.getOutputStream().print(fileName + ".mp3");
    }
}
