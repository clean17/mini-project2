package shop.mtcoding.project.controller;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.project.dto.apply.ApplyResp.ApplyStatusUserRespDto;
import shop.mtcoding.project.model.apply.Apply;
import shop.mtcoding.project.model.apply.ApplyRepository;
import shop.mtcoding.project.model.notify.NotifyRepository;
import shop.mtcoding.project.model.user.User;

@Controller
@RequiredArgsConstructor
public class SseController {
    private final ApplyRepository applyRepository;
    private final NotifyRepository notifyRepository;
    private final HttpSession session;

    @GetMapping(value = "/notify", produces = "text/event-stream")
    public ResponseEntity<SseEmitter> notify(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return ResponseEntity.badRequest().build();
        }
        SseEmitter emitter = new SseEmitter();
        CompletableFuture.runAsync(() -> {
            try {
                List<Apply> aDtos = applyRepository.findByUserIdToNotice(principal.getUserId());
                // boolean notice = false;
                Integer count = 0;
                for (Apply aDto : aDtos) {
                    Integer state = 1;
                    try {
                        state = notifyRepository.findByApplyId(aDto.getApplyId()).getState();
                        // System.out.println("테스트 : 알림상태"+ state + "     지원상태"+ aDto.getState());
                    } catch (Exception e) {
                    }
                    if( state == 0){
                        count ++;
                    }
                    try {
                        notifyRepository.update(aDto.getApplyId(), null);
                    } catch (Exception e) {
                    }
                }
                if ( count > 0 ){
                    emitter.send("지원 결과가 나왔습니다. 마이홈에서 확인해주세요");
                }
                count = 0 ;
                // System.out.println("테스트 : 카운트 초기화");
            } catch (Exception e) {
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }
        });
        return ResponseEntity.ok(emitter);
    }
}
