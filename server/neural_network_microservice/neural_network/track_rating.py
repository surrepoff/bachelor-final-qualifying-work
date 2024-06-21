from api.repository import RatingRepository


class TrackRatingService:
    @classmethod
    async def get_track_rating(cls, user_id: int):
        rating = {}

        track_rating = await cls.get_rating_from_user_track(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_album(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_artist(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_playlist(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_track_rating(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_track_history(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_album_rating(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_artist_rating(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_playlist_rating(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        track_rating = await cls.get_rating_from_user_recommendation_rating(user_id)
        rating = await cls.merge_track_ratings(rating, track_rating)

        return rating

    @classmethod
    async def merge_track_ratings(cls, rating, track_rating):
        for track_id in track_rating:
            if track_id in rating:
                rating[track_id] += track_rating[track_id]
            else:
                rating[track_id] = track_rating[track_id]
        return rating

    @classmethod
    async def get_rating_from_user_track(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_track(user_id)
        for track_rating in track_ratings:
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += 50
            else:
                rating[track_rating.track_id] = 50
        return rating

    @classmethod
    async def get_rating_from_user_album(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_album(user_id)
        for track_rating in track_ratings:
            points = 50 / track_rating.track_count
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += points
            else:
                rating[track_rating.track_id] = points
        return rating

    @classmethod
    async def get_rating_from_user_artist(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_album(user_id)
        for track_rating in track_ratings:
            points = 50 / track_rating.track_count
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += points
            else:
                rating[track_rating.track_id] = points
        return rating

    @classmethod
    async def get_rating_from_user_playlist(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_playlist(user_id)
        for track_rating in track_ratings:
            points = 50 / track_rating.track_count
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += points
            else:
                rating[track_rating.track_id] = points
        return rating

    @classmethod
    async def get_rating_from_user_track_rating(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_track_rating(user_id)
        for track_rating in track_ratings:
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += track_rating.user_rating_id * 100
            else:
                rating[track_rating.track_id] = track_rating.user_rating_id * 100
        return rating

    @classmethod
    async def get_rating_from_user_track_history(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_track_history(user_id)
        for track_rating in track_ratings:
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += 10
            else:
                rating[track_rating.track_id] = 10
        return rating

    @classmethod
    async def get_rating_from_user_album_rating(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_album_rating(user_id)
        for track_rating in track_ratings:
            if track_rating.track_count > 0:
                points = 100 / track_rating.track_count
            else:
                points = 0
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += track_rating.user_rating_id * points
            else:
                rating[track_rating.track_id] = track_rating.user_rating_id * points
        return rating

    @classmethod
    async def get_rating_from_user_artist_rating(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_artist_rating(user_id)
        for track_rating in track_ratings:
            if track_rating.track_count > 0:
                points = 100 / track_rating.track_count
            else:
                points = 0
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += track_rating.user_rating_id * points
            else:
                rating[track_rating.track_id] = track_rating.user_rating_id * points
        return rating

    @classmethod
    async def get_rating_from_user_playlist_rating(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_playlist_rating(user_id)
        for track_rating in track_ratings:
            if track_rating.track_count > 0:
                points = 100 / track_rating.track_count
            else:
                points = 0
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += track_rating.user_rating_id * points
            else:
                rating[track_rating.track_id] = track_rating.user_rating_id * points
        return rating

    @classmethod
    async def get_rating_from_user_recommendation_rating(cls, user_id: int):
        rating = {}
        track_ratings = await RatingRepository.get_rating_from_user_recommendation_rating(user_id)
        for track_rating in track_ratings:
            if track_rating.track_count > 0:
                points = 100 / track_rating.track_count
            else:
                points = 0
            if track_rating.track_id in rating:
                rating[track_rating.track_id] += track_rating.user_rating_id * points
            else:
                rating[track_rating.track_id] = track_rating.user_rating_id * points
        return rating
