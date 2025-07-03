import com.example.rickandmorty.network.CharacterApiService
import com.example.rickandmorty.network.EpisodeApiService
import com.example.rickandmorty.network.LocationApiService
import com.example.rickandmorty.network.RetrofitInstance
import com.example.rickandmorty.repository.CharacterRepository
import com.example.rickandmorty.repository.EpisodeRepository
import com.example.rickandmorty.repository.LocationRepository
import com.example.rickandmorty.viewmodel.CharacterViewModel
import com.example.rickandmorty.viewmodel.EpisodeViewModel
import com.example.rickandmorty.viewmodel.LocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<CharacterApiService> { RetrofitInstance.characterApiService }
    single<EpisodeApiService> { RetrofitInstance.episodeApiService }
    single<LocationApiService> { RetrofitInstance.locationApiService }

    single { CharacterRepository(get()) }
    single { EpisodeRepository(get()) }
    single { LocationRepository(get()) }

    viewModel { CharacterViewModel(get()) }
    viewModel { EpisodeViewModel(get()) }
    viewModel { LocationViewModel(get()) }
}
