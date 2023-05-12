package com.silverorange.videoplayer.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silverorange.videoplayer.data.models.Author
import com.silverorange.videoplayer.data.models.VideoData
import com.silverorange.videoplayer.domain.VideoRepository
import com.silverorange.videoplayer.presentation.states.VideoPlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


interface VideoPlayerEvents {
    fun nextVideo ()
    fun previousVideo ()
}

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel(), VideoPlayerEvents {

    companion object {
        private val TAG = VideoPlayerViewModel::class.java.simpleName
    }

    private val _state = MutableStateFlow(VideoPlayerState.empty())
    val state = _state.asStateFlow()

    init {
        //setStateWithDummyData()
        retrieveVideoList()
    }

    override fun nextVideo() {
        if (_state.value.selectedVideoIndex < _state.value.videos.size - 1) {
            _state.update {
                it.copy(
                    selectedVideoIndex = it.selectedVideoIndex + 1
                )
            }
        }
    }

    override fun previousVideo() {
        if (_state.value.selectedVideoIndex > 0) {
            _state.update {
                it.copy(
                    selectedVideoIndex = it.selectedVideoIndex - 1
                )
            }
        }
    }

    private fun retrieveVideoList() {
        viewModelScope.launch {
            videoRepository.getVideoList().onSuccess {
                Log.d(TAG, "retrieveVideoList: $it")
                sortVideosByDate(it)
            }.onFailure {
                Log.e(TAG, "error in retrieveVideoList: ", it)
            }
        }
    }

    private fun sortVideosByDate(videos: List<VideoData>) {
        _state.update {
            it.copy(
                videos = videos.sortedByDescending { it.publishedAt },
                selectedVideoIndex = 0
            )
        }
        Log.d(TAG, "sortVideosByDate: ${_state.value.videos}")
    }

    private fun setStateWithDummyData() {
        _state.update {
            it.copy(
                videos = listOf(
                    VideoData(
                        id = "2f1fe9c0-bdbf-4104-bee2-3c0ec514436f",
                        title = "About EM:RAP",
                        hlsUrl = "https://d140vvwqovffrf.cloudfront.net/media/5e852de33c8e4/hls/index.m3u8",
                        fullUrl = "https://d140vvwqovffrf.cloudfront.net/media/5e852de33c8e4/720.mp4",
                        description = "# Omnibus quae\n\n## Sit sed inque teneris\n\nLorem markdownum adhuc securosque, suo ponto satae se adhuc, terga. Fuit campus,\nqui, quod **formasque coeunt artus**, certamina nurus nunc coniuge peregit ritu.\nSuperat funera perque ferrem corpora saevam caespite nam maternaque sumpserat.\n\nIuga adest? Humano percussit Haemum frugis, vix qua quae iniustaque et tibi,\ntenuisset vix lacrimis umbra, ire. Ante nec. Est deam vivum ad ponti, non\ncaelamina et Sisyphon, inhonestaque atque, sed et vivit.\n\n1. Litora gloria Hyperionis carmina ungulaque patiere\n2. Dari nasci\n3. Fragilesque tendo dixit resonant quoque\n4. Et segnibus fuge ter Armeniae templi\n5. Sit deponere credit non aquarum Achilles tenus\n6. Exsultat artes Iovi\n\nStupet honoris. Nunc angebar Dryasque.\n\n## Multamque siqua hospitibus patrio\n\nExstimulata blando; qui armis Ixion **finire laetusque** sororem, aut **et**,\nigne membra? Nec bello ramis pari, Libye percensuit metallis, *in* mediis\nprocumbere.\n\n- Et illa\n- Clanis sceleris\n- Velletque prolisque auditum\n- Res delius iraque Semeles veluti\n- Cava umor quid superest fama artus\n\nTutam auditurum ab erit sum frigore in fleturi vectus eodem *quinos capillos me*\nagros. Ut rogis **foret cogor** neve ultoris rabiem, pro perque meque ossa\n[alter fassusque](http://www.querellissacra.com/reminiscitur) proxima erigitur,\nest *occulte concutio*. Celsa Echo huc credunt natus leones racemifero condi\nPhlegyanque esse; suis, per. Versum fessam me reponit omnis demisere litore\ncorpora esse expers veteris Noctisque.\n\n1. Inhaerebat undas ungula pulsat subiit modo\n2. Madefacta Cyanen\n3. Qua ex Tartessia nulla dixi illum lumina\n4. Et et viridi serpens ast longoque agros\n5. Aras nos arma medium consonat cum desit\n6. Facta ille ego\n\nFibris animo. Tui nota quod iter caeli mirata pede paulatim purpureum tergo\nseptemplicis? Progenies ait. Patrios sis est exclamat exiguo Myrrha in tibi\nexarsit cum. Flagrantemque cape: iter capacem cum rerum quota pennisque pone non\npatria.",
                        publishedAt = "2018-12-14T21:09:00.000Z",
                        author = Author(
                            id = "2cab326a-ab2f-4624-a6d7-2e1855fc5e4e",
                            name = "Mel Herbert"
                        )
                    ),
                    VideoData(
                        id = "6ec246b1-ad09-4e03-8573-21e2e779856c",
                        title = "EM:RAP Global Outreach",
                        hlsUrl = "https://d140vvwqovffrf.cloudfront.net/media/5e87b9a811599/hls/index.m3u8",
                        fullUrl = "https://d140vvwqovffrf.cloudfront.net/media/5e87b9a811599/full/720.mp4",
                        description = "# Famemque horrescere occasus neve\\n\\n## Ityosque oraque subvolat patetis\\n\\nLorem markdownum purpura Scyrum, ira aper cruribus purpurea at neve\\npraecordiaque illa de erat. Lapithae pollice; nec *aret principiis* sua preces\\nregia nam? Templa cruorem sparsos **ardua** protinus *subtraheret fruge*\\nexemplum, esse, toros. Ubi quibus sparsaque tutela.\\n\\n- Suo rogat lumina puto ut frater Lycetum\\n- Illos dilecta diva\\n- Quoniam Daedalus\\n- Vulnere coniuge trisulcis nunc leves\\n- Una veluti mea Achivi ferum pectus\\n- Pressit tu Iovis mutantur sedere Credulitas ambage\\n\\n*Vitataque Fame cornibus* volubilitas ad nec insania petito adrectisque ipsa\\nanimasque pugnabant ferrum parant. Sensit haec promissaque currus. Tauri levius\\ntetigisset dixere est mora ponti, ea posuit pedem circuit: viribus, iterum, e\\nseque! Temptare perpetuo ferit non telis Hippotaden milibus valuissent\\n[ait](http://incensaquefaveas.com/aera), corpore volenti instruxere Anienis\\namor, bis saxum?\\n\\n## Regemque miscuit capit amanti\\n\\nTalibus haustus, nec ficti: ille una lacertis praeter dixerat inputet.\\n[Instabiles illam](http://cerniset.net/sacraangustis), suique [mentitus manus\\nserpente](http://www.opportunacadentem.org/certum): conataque artes latos!\\n\\n- Alter sua\\n- Amentis clamor\\n- Suspirat albentibus delusa barbariam Gigantas umbras at\\n- Vertit iuventus\\n- Ergo conspexi aratri infausto audacia esset\\n\\n## Rebus in parantem dignum ruptosque collis certamine\\n\\nNon Creten mirabere concrescere ipse virgineos quos et mensis iunget, nullamque\\nLibycas. Uterum monimenta nunc obliquo fiant **illa** ante haeremusque opesque\\nOthrys, rursusque indoctum ignibus **comites Iri**. Sermonibus ripa flectant\\npudibundaque ardet est medioque nobilis, heres me genetrix videritis illa. Humo\\ndum ac accepit mando imagine similis, ingemuit ora omnes offensa omnia causa\\nuberibus fertque aequora?\\n\\nFrustra fecit pan! Unum deosque membra: nova: inter sic genitor terra somni\\nAeoliden quam telae. Haec gravis non facies lata mille, procorum possem scopulo,\\nsibi sic maius iussa; ante caelestum ungues, opem. Ramis sanguine, si *nec\\nmiseris scitatur* pereuntem comites, vult et cani cur ventorum, tuebere\\ntenuaverat, multi.\\n\\nNeque gemitu. Quo moles ex quis certa adhaesit hanc saltu nigrae [adeundi\\ncarinae](http://vultus-antiquas.io/) vobis murmure haberet ira factis? Et\\n**adiere** inplevere sibi, retia.",
                        publishedAt = "2019-12-15T22:17:00.000Z",
                        author = Author(
                            id = "2cab326a-ab2f-4624-a6d7-2e1855fc5e4e",
                            name = "Mel Herbert"
                        )
                    ),
                ),
                isLoading = false
            )
        }
    }


}