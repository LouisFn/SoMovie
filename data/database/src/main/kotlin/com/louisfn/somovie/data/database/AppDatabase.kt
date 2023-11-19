package com.louisfn.somovie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.louisfn.somovie.data.database.converter.*
import com.louisfn.somovie.data.database.dao.*
import com.louisfn.somovie.data.database.entity.*

@Database(
    entities = [
        RemoteKeyEntity::class,
        MovieEntity::class,
        MovieImageEntity::class,
        MovieGenreCrossRefEntity::class,
        MovieProductionCompanyCrossRefEntity::class,
        MovieProductionCountryEntity::class,
        GenreEntity::class,
        ActorEntity::class,
        CrewMemberEntity::class,
        CompanyEntity::class,
        PersonEntity::class,
        ExploreEntity::class,
        YoutubeVideoEntity::class,
        WatchlistEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    ExploreCategoryConverter::class,
    LocalDateConverter::class,
    InstantConverter::class,
    DurationConverter::class,
    RemoteKeyTypeConverter::class,
    OffsetDateTimeConverter::class,
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun movieDao(): MovieDao
    abstract fun movieImageDao(): MovieImageDao
    abstract fun movieGenreCrossRefDao(): MovieGenreCrossRefDao
    abstract fun movieProductionCompanyCrossRefDao(): MovieProductionCompanyCrossRefDao
    abstract fun genreDao(): GenreDao
    abstract fun actorDao(): ActorDao
    abstract fun crewMemberDao(): CrewMemberDao
    abstract fun companyDao(): CompanyDao
    abstract fun productionCountryDao(): ProductionCountryDao
    abstract fun personDao(): PersonDao
    abstract fun exploreDao(): ExploreDao
    abstract fun youtubeVideoDao(): YoutubeVideoDao
    abstract fun watchListDao(): WatchlistDao
}
