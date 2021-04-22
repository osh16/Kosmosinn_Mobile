package is.hi.hbv501g.kosmosinn.Kosmosinn.Security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private String tokenSecret;

	public JWTAuthorizationFilter(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		System.out.println("=============");
		System.out.println("cringe");
		System.out.println("TOKENSECRET: " + tokenSecret);
		System.out.println("TOKENSECRET: " + tokenSecret);
		System.out.println("TOKENSECRET: " + tokenSecret);
		System.out.println("TOKENSECRET: " + tokenSecret);
		System.out.println("=============");
		try {
			if (checkJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			}else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		System.out.println("=============");
		System.out.println("validateToken");
		System.out.println("TOKENSECRET: " + tokenSecret);
		System.out.println("JWT TOKEN: " + jwtToken);
		System.out.println("=============");
		return Jwts.parser().setSigningKey(tokenSecret.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

		System.out.println(SecurityContextHolder.getContext().toString());
	}

	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}

}