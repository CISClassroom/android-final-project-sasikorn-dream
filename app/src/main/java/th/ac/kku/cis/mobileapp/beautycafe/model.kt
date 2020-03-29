package th.ac.kku.cis.mobileapp.beautycafe

//class model(var Topicname:String,var TipsOfHair_id :String)
class model {
    companion object Factory {
        fun create(): model = model()
    }
    var Topicname: String? = null
    var TipsOfHair_id: String? = null
}