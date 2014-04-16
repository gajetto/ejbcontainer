$( document ).ready(function() {
  searchUsers("");
});

function searchUsers(search){
    var length = $("#userSearch").val().length;
    
    if (length >= 0){
        $.post("ServletGetUser",{ userSearch:search } ,function(data){
            $('#userResult').find('.results').remove();
            if(data!=''){
                
                console.log(data);
                $('#userSearchHeader').show();

                $.each(data, function(i, user){
                    var userName = user.userName;
                    var firstName = user.firstName;
                    var lastName = user.lastName;
                    var dob = user.dateOfBirth;
                    var isAdmin = user.isAdmin;
                    var row = $('<tr class="results">\n\
                                    <td>'+userName+'</td>\n\
                                    <td>'+firstName+'</td>\n\
                                    <td>'+lastName+'</td>\n\
                                    <td>'+dob+'</td>\n\
                                    <td>'+isAdmin+'</td>\n\
                                    <td class="center"><a href="manageforms?edit='+userName+'"><div><img src="images/edit-icon.png" alt="edit" /></div></a></td>\n\
                                </tr>');
                    row.appendTo($('#userResult'));
                })
            }else{
                $('#userSearchHeader').hide();
            }
        });
              
	}
        else{
            $('#userResult').find('.results').remove();
            $('#userSearchHeader').hide();
	}
}