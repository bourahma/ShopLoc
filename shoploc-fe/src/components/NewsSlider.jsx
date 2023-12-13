import { useEffect, useState } from "react";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

export default function NewsSlider() {
    const [newsData, setNewsData] = useState([]);
    const sliderSettings = {
        dots: false,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        arrows: false,
    };
    useEffect(() => {
        const fetchData = async () => {
            try {
                const apiKey = "0b2b3a43fb7644d78a4c5ef6708936c6"; // Replace with your News API key
                const response = await fetch(
                    `https://newsapi.org/v2/everything?domains=actu.fr&apiKey=${apiKey}`
                );

                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }

                const data = await response.json();
                setNewsData(data.articles);
            } catch (error) {
                console.error("Error fetching news:", error);
            }
        };

        fetchData();
    }, []);

    return (
        <Slider {...sliderSettings} className="flex-1 relative">
            <div className="md:h-96 relative">
                <p className="text-black absolute top-4 left-4 font-bold text-lg text-center">
                    Actualité en France
                </p>
            </div>
            {newsData.map((article) => (
                <div key={article.url} className="md:h-96 relative">
                    <img
                        src={article.urlToImage}
                        alt={article.title}
                        className="w-full h-full object-cover"
                    />
                    <div className="absolute inset-0 flex flex-col items-center justify-center text-white bg-black bg-opacity-50 p-6">
                        <h3 className="text-2xl font-bold mb-2">
                            {article.title}
                        </h3>
                        <p className="text-sm">{article.description}</p>
                    </div>
                </div>
            ))}
        </Slider>
    );
}
