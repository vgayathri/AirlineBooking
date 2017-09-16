package airline.model;

/**
 * Created by Gayathri on 07/09/17.
 */
public class SeatsInfo {
    private Integer totalNoOfSeats;
    private Integer noOfSeatsBooked;
    private Float basePricePerSeat;

    public SeatsInfo(Integer noOfAllocatedSeats, Integer noOfSeatsBooked, Float basePricePerSeat) {
        this.totalNoOfSeats = noOfAllocatedSeats;
        this.noOfSeatsBooked = noOfSeatsBooked;
        this.basePricePerSeat = basePricePerSeat;
    }

    public SeatsInfo(Integer noOfAllocatedSeats, Float basePricePerSeat) {
        this.totalNoOfSeats = noOfAllocatedSeats;
        this.noOfSeatsBooked = 0;
        this.basePricePerSeat = basePricePerSeat;
    }

    public Integer getTotalNoOfSeats() {
        return totalNoOfSeats;
    }

    public Integer getNoOfSeatsBooked() {
        return noOfSeatsBooked;
    }

    public void setNoOfSeatsBooked(Integer noOfSeatsBooked) {
        this.noOfSeatsBooked = noOfSeatsBooked;
    }

    public Float getBasePricePerSeat() {
        return basePricePerSeat;
    }

    public Integer getNoOfSeatsAvlb() {
        return totalNoOfSeats - noOfSeatsBooked;
    }

    public Float calculatePriceofSeats(Integer noOfSeats) {
        return (basePricePerSeat * noOfSeats);
    }

    public float getPercentageOfSeatsOccuped() {
        return (float)(noOfSeatsBooked*100/totalNoOfSeats);
    }

}
