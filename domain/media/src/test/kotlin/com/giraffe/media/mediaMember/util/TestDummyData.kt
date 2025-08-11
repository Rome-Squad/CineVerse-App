package com.giraffe.media.mediaMember.util

import com.giraffe.media.mediaMember.entity.core.SocialMediaLinks
import com.giraffe.media.mediaMember.repository.MediaMemberRepository

object TestDummyData {

    val castMember1 = createCastMember(
        id = 3895,
        name = "Michael Caine",
        biography = "A legendary British actor...",
        birthday = "1933-03-14",
        placeOfBirth = "Rotherhithe, London, England, UK",
        imageUrl = "https://image.tmdb.org/t/p/w500/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg",
        otherImages = listOf(
            "/bVZRMlpjTAO2pJK6v90buFgVbSW.jpg",
            "/r3U4n9Ti6UrY5m84wzJVTKeBgeC.jpg"
        ),
        socialMedia = SocialMediaLinks(
            facebookLink = "michaelcaine",
            instagramLink = "michaelcaine_official",
            youtubeLink = "UCV1n8z2d3a5b6c7d8e9f0g1h"
        ),
        role = "Actor"
    )

    val castMember2 = createCastMember(
        id = 1,
        name = "Christian Bale",
        role = "Acting",
        characterName = "Bruce Wayne / Batman",
        imageUrl = "https://image.tmdb.org/t/p/w500/christian_bale.jpg"
    )

    val castMember3 = createCastMember(
        id = 2,
        name = "Heath Ledger",
        role = "Acting",
        characterName = "The Joker",
        imageUrl = "https://image.tmdb.org/t/p/w500/heath_ledger.jpg"
    )

    val castMembers = listOf(castMember2, castMember3)

    val crewMembers = listOf(
        createCrewMember(
            id = 1,
            name = "Christopher Nolan",
            role = "Director",
        )
    )

    val mediaMembers = MediaMemberRepository.MediaMembers(
        cast = castMembers,
        crew = crewMembers
    )
}