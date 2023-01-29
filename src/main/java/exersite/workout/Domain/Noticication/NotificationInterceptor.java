package exersite.workout.Domain.Noticication;

import exersite.workout.Config.PrincipalDetails;
import exersite.workout.Domain.Member.Member;
import exersite.workout.Repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationRepository notificationRepository;
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (modelAndView != null && authentication != null) {
            Member member = ((PrincipalDetails) authentication.getPrincipal()).getMember();
            long count = notificationRepository.countByMemberAndChecked(member, false);
            modelAndView.addObject("hasNotification", count > 0);
        }
    }
}
