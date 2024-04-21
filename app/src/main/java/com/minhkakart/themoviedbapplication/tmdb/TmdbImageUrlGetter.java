package com.minhkakart.themoviedbapplication.tmdb;

import com.minhkakart.themoviedbapplication.configure.EnvironmentVariable;

public class TmdbImageUrlGetter {

    private static final String BASE_IMAGE_URL = EnvironmentVariable.IMAGE_URL;

    /**
     * Returns the URL for a small backdrop image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param backdropPath The path of the backdrop image.
     * @return The complete URL for the small backdrop image.
     */
    public static String getBackdropSmallUrl(String backdropPath) {
        return BASE_IMAGE_URL + "w300" + backdropPath;
    }


    /**
     * Returns the URL for a medium backdrop image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param backdropPath The path of the backdrop image.
     * @return The complete URL for the medium backdrop image.
     */
    public static String getBackdropMediumUrl(String backdropPath) {
        return BASE_IMAGE_URL + "w780" + backdropPath;
    }

    /**
     * Returns the URL for a large backdrop image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param backdropPath The path of the backdrop image.
     * @return The complete URL for the large backdrop image.
     */
    public static String getBackdropLargeUrl(String backdropPath) {
        return BASE_IMAGE_URL + "w1280" + backdropPath;
    }

    /**
     * Returns the URL for an extra large backdrop image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param backdropPath The path of the backdrop image.
     * @return The complete URL for the extra large backdrop image.
     */
    public static String getBackdropOriginalUrl(String backdropPath) {
        return BASE_IMAGE_URL + "original" + backdropPath;
    }

    /**
     * Returns the URL for a tiny logo image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param logoPath The path of the logo image.
     * @return The complete URL for the tiny logo image.
     */
    public static String getLogoTinyUrl(String logoPath) {
        return BASE_IMAGE_URL + "w45" + logoPath;
    }

    /**
     * Returns the URL for a small logo image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param logoPath The path of the logo image.
     * @return The complete URL for the small logo image.
     */
    public static String getLogoSmallUrl(String logoPath) {
        return BASE_IMAGE_URL + "w92" + logoPath;
    }

    /**
     * Returns the URL for a medium logo image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param logoPath The path of the logo image.
     * @return The complete URL for the medium logo image.
     */
    public static String getLogoMediumUrl(String logoPath) {
        return BASE_IMAGE_URL + "w154" + logoPath;
    }

    /**
     * Returns the URL for a large logo image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param logoPath The path of the logo image.
     * @return The complete URL for the large logo image.
     */
    public static String getLogoLargeUrl(String logoPath) {
        return BASE_IMAGE_URL + "w185" + logoPath;
    }

    /**
     * Returns the URL for an extra large logo image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param logoPath The path of the logo image.
     * @return The complete URL for the extra large logo image.
     */
    public static String getLogoExtraLargeUrl(String logoPath) {
        return BASE_IMAGE_URL + "w300" + logoPath;
    }

    /**
     * Returns the URL for an extra extra large logo image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param logoPath The path of the logo image.
     * @return The complete URL for the extra extra large logo image.
     */
    public static String getLogoExtraExtraLargeUrl(String logoPath) {
        return BASE_IMAGE_URL + "w500" + logoPath;
    }

    /**
     * Returns the URL for the original logo image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param logoPath The path of the logo image.
     * @return The complete URL for the original logo image.
     */
    public static String getLogoOriginalUrl(String logoPath) {
        return BASE_IMAGE_URL + "original" + logoPath;
    }

    /**
     * Returns the URL for a tiny poster image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param posterPath The path of the poster image.
     * @return The complete URL for the tiny poster image.
     */
    public static String getPosterTinyUrl(String posterPath) {
        return BASE_IMAGE_URL + "w92" + posterPath;
    }

    /**
     * Returns the URL for a small poster image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param posterPath The path of the poster image.
     * @return The complete URL for the small poster image.
     */
    public static String getPosterSmallUrl(String posterPath) {
        return BASE_IMAGE_URL + "w154" + posterPath;
    }

    /**
     * Returns the URL for a medium poster image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param posterPath The path of the poster image.
     * @return The complete URL for the medium poster image.
     */
    public static String getPosterMediumUrl(String posterPath) {
        return BASE_IMAGE_URL + "w185" + posterPath;
    }

    /**
     * Returns the URL for a large poster image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param posterPath The path of the poster image.
     * @return The complete URL for the large poster image.
     */
    public static String getPosterLargeUrl(String posterPath) {
        return BASE_IMAGE_URL + "w342" + posterPath;
    }

    /**
     * Returns the URL for an extra large poster image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param posterPath The path of the poster image.
     * @return The complete URL for the extra large poster image.
     */
    public static String getPosterExtraLargeUrl(String posterPath) {
        return BASE_IMAGE_URL + "w500" + posterPath;
    }

    /**
     * Returns the URL for an extra extra large poster image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param posterPath The path of the poster image.
     * @return The complete URL for the extra extra large poster image.
     */
    public static String getPosterExtraExtraLargeUrl(String posterPath) {
        return BASE_IMAGE_URL + "w780" + posterPath;
    }

    /**
     * Returns the URL for the original poster image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param posterPath The path of the poster image.
     * @return The complete URL for the original poster image.
     */
    public static String getPosterOriginalUrl(String posterPath) {
        return BASE_IMAGE_URL + "original" + posterPath;
    }

    /**
     * Returns the URL for a small profile image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param profilePath The path of the profile image.
     * @return The complete URL for the small profile image.
     */
    public static String getProfileSmallUrl(String profilePath) {
        return BASE_IMAGE_URL + "w185" + profilePath;
    }

    /**
     * Returns the URL for a medium profile image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param profilePath The path of the profile image.
     * @return The complete URL for the medium profile image.
     */
    public static String getProfileMediumUrl(String profilePath) {
        return BASE_IMAGE_URL + "h632" + profilePath;
    }

    /**
     * Returns the URL for a large profile image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param profilePath The path of the profile image.
     * @return The complete URL for the large profile image.
     */
    public static String getProfileLargeUrl(String profilePath) {
        return BASE_IMAGE_URL + "h632" + profilePath;
    }

    /**
     * Returns the URL for the original profile image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param profilePath The path of the profile image.
     * @return The complete URL for the original profile image.
     */
    public static String getProfileOriginalUrl(String profilePath) {
        return BASE_IMAGE_URL + "original" + profilePath;
    }

    /**
     * Returns the URL for a small still image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param stillPath The path of the still image.
     * @return The complete URL for the small still image.
     */
    public static String getStillSmallUrl(String stillPath) {
        return BASE_IMAGE_URL + "w92" + stillPath;
    }

    /**
     * Returns the URL for a medium still image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param stillPath The path of the still image.
     * @return The complete URL for the medium still image.
     */
    public static String getStillMediumUrl(String stillPath) {
        return BASE_IMAGE_URL + "w185" + stillPath;
    }

    /**
     * Returns the URL for a large still image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param stillPath The path of the still image.
     * @return The complete URL for the large still image.
     */
    public static String getStillLargeUrl(String stillPath) {
        return BASE_IMAGE_URL + "w300" + stillPath;
    }

    /**
     * Returns the URL for the original still image.
     * <p>
     * This method concatenates the base URL, a specific path, and the input parameter to form a complete URL.
     *
     * @param stillPath The path of the still image.
     * @return The complete URL for the original still image.
     */
    public static String getStillOriginalUrl(String stillPath) {
        return BASE_IMAGE_URL + "original" + stillPath;
    }

}
