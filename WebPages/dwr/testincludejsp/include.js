
function forward() {
	Include.setPage("/page2.jsp");
	Include.getInclude(function(data) {
    dwr.util.setValue("forward", data, { escapeHtml:false});
  });
}
