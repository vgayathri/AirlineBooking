package airline.model;

/**
 * Created by Gayathri on 07/09/17.
 */
public class SeatsInfo {
    Integer noOfAllocatedSeats;
    Integer noOfSeatsBooked;
    Float basePricePerSeat;

    public SeatsInfo(Integer noOfAllocatedSeats, Integer noOfSeatsBooked, Float basePricePerSeat) {
        this.noOfAllocatedSeats = noOfAllocatedSeats;
        this.noOfSeatsBooked = noOfSeatsBooked;
        this.basePricePerSeat = basePricePerSeat;
    }

    public SeatsInfo(Integer noOfAllocatedSeats, Float basePricePerSeat) {
        this.noOfAllocatedSeats = noOfAllocatedSeats;
        this.noOfSeatsBooked = 0;
        this.basePricePerSeat = basePricePerSeat;
    }

    public Integer getNoOfAllocatedSeats() {
        return noOfAllocatedSeats;
    }

    public void setNoOfAllocatedSeats(Integer noOfAllocatedSeats) {
        this.noOfAllocatedSeats = noOfAllocatedSeats;
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

    public void setBasePricePerSeat(Float basePricePerSeat) {
        this.basePricePerSeat = basePricePerSeat;
    }
    public Integer getNoOfSeatsAvlb() {
        return noOfAllocatedSeats - noOfSeatsBooked;
    }

    public Float calculatePriceofSeats(Integer noOfSeats) {
        return basePricePerSeat * noOfSeats;
    }


}
