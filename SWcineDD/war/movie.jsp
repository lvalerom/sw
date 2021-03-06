<!DOCTYPE html>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="../css/index.css">

<title>CINE</title>

</head>

<body>

<%@ page import="com.lvaleromsw.swcine.persistence.Movie" %>
<%@ page import="com.lvaleromsw.swcine.dao.MovieDAO"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.io.OutputStream" %>

<%
try{
	String m = request.getParameter("movie");
	if(m == null) response.sendRedirect("/index.jsp");
	Movie mov = MovieDAO.getInstance().getMovie(Long.valueOf(m));
	if(mov == null) response.sendRedirect("../index.jsp");
	session.setAttribute("url","/movie.jsp?movie="+m);
%>

<%@ include file="topbanner.jsp" %>

<div class="cine_content_wrapper">
	<%@ include file="leftmenu.jsp" %>
	
	<div class="cine_main_wrapper">
		<div class="cine_moviecard_title">
		<h1><% out.println(mov.getTitle()); %></h1>
			<% if(session.getAttribute("admin") != null){ %>
			<form action="#"><input type="button" onClick="openEditMovie()" id="button-edit" value="Editar"></form>
			<script>
				function openEditMovie() {
					location.href="../newmovie.jsp?movie=<%=mov.getKey().getId()%>";
				}
			</script>
			<form action="#"><input type="button" onClick="openDelMovie()" id="button-del" value="Eliminar"></form>
			<script>
				function openDelMovie() {
					location.href="../moviedelservlet?movie=<%=mov.getKey().getId()%>";
				}
			</script>
			<% } %>
		</div>
		<div class="cine_moviecard_content">
			<div class="cine_moviecard_content_info">
				<div class="cine_moviecard_content_info_text">
					<dl>
						<dt>T&iacute;tulo</dt>
						<dd><% out.println(mov.getMovieTitle()); %></dd>
						<dt>T&iacute;tulo original</dt>
						<dd><% out.println(mov.getRealMovieTitle()); %></dd>
						<dt>A&ntilde;o</dt>
						<dd><% out.println(mov.getDate()); %></dd>
						<dt>Pa&iacute;s</dt>
						<dd><% out.println(mov.getCountry()); %></dd>
						<dt>Director</dt>
						<dd><% out.println(mov.getDirector()); %></dd>
						<dt>Reparto</dt>
						<dd><% out.println(mov.getCasting()); %></dd>
						<dt>G&eacute;nero</dt>
						<dd><% out.println(mov.getGenre()); %></dd>
						<dt>Sinopsis</dt>
						<dd><% out.println(mov.getSynopsis()); %></dd>
						<dt>Comentarios</dt>
						<dd>
							<div class="cine_moviecard_comments">
								<% 
									Vector<String> comments = mov.getComments();
									for(int i = 0; i < comments.size(); i++) { %>
								<div class="cine_moviecard_comment">
									<div class="cine_moviecard_comment_text">
										<p>
										<%out.println(comments.get(i)); %>
										</p>
									</div>
									<div class="cine_moviecard_comment_author">
										<p>
										<% i = i + 1; out.println(comments.get(i));%>
										</p>
									</div>
								</div>
								<% } %>
								<% if(session.getAttribute("username") != null){ %>
								<div class="cine_moviecard_comment_frame">
									<form action="moviecommentaddservlet?movie=<%out.println(mov.getKey().getId());%>" method="post">
										<input type="text" name="comment" size="63" value="">
										<input type="submit" id="button-coment" value="Comentar">
									</form>
								</div>
								<% } %>
							</div>
						</dd>
					</dl>
				</div>
				<div class="cine_moviecard_content_info_pic">
					<img alt="" src="showimageservlet?movie=<%=m %>" width="100" height="150">
				</div>
				<div class="cine_clear"></div>
			</div>
			<div class="cine_moviecard_content_synopsis">
			</div>
			</div>
	</div>
	<div class="cine_clear"></div>
</div>

<%@ include file="bottombanner.jsp" %>
<%
}catch(java.lang.IllegalArgumentException e){
	response.sendRedirect("index.jsp");
}catch(Exception e){
	response.sendRedirect("index.jsp");
}
%>
</body>
</html>