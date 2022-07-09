package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//resolveArgument(): 컨트롤러 호출 직전에 호출 되어서 필요한 파라미터 정보를 생성해준다. ex- 세션에 있는 로그인 회원 정보인 member 객체를 찾아서 반환.
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        log.info("supportsParameter 실행");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);   // @Login이 파라미터에 있느냐
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());  //@Login 뒤에 선언 되어있는 것이 Member 타입이냐.

        return hasLoginAnnotation && hasMemberType;  //true를 반환하면 resolveArgument()이 호출 됨.
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null) {
            return null;
        }

        Object member = session.getAttribute(SessionConst.LOGIN_MEMBER);
        return member;
    }
}

