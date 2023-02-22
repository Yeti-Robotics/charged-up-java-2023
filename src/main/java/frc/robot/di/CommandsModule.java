package frc.robot.di;

import dagger.Module;

@Module
public class CommandsModule {
//    @Provides
//    @IntoMap
//    @ClassKey(MoveElevatorDownCommand.class)
//    public CommandFactory provideMoveElevatorDownCommand(ElevatorSubsystem elevatorSubsystem) {
//        return new MoveElevatorDownCommand(elevatorSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(MoveElevatorUpCommand.class)
//    public CommandFactory provideMoveElevatorUpCommand(ElevatorSubsystem elevatorSubsystem) {
//        return new MoveElevatorUpCommand(elevatorSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(SetElevatorPositionConeHandoffCommand.class)
//    public CommandFactory provideSetElevatorPositionConeHandoffCommand(ElevatorSubsystem elevatorSubsystem) {
//        return new SetElevatorPositionConeHandoffCommand(elevatorSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(SetElevatorPositionCommand.class)
//    public CommandFactory provideSetElevatorPositionTopCommand(ElevatorSubsystem elevatorSubsystem) {
//        return new SetElevatorPositionCommand(elevatorSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(SetElevatorPositionMidCommand.class)
//    public CommandFactory provideSetElevatorPositionMidCommand(ElevatorSubsystem elevatorSubsystem) {
//        return new SetElevatorPositionMidCommand(elevatorSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(SetElevatorPositionDownCommand.class)
//    public CommandFactory provideSetElevatorPositionDownCommand(ElevatorSubsystem elevatorSubsystem) {
//        return new SetElevatorPositionDownCommand(elevatorSubsystem);
//    }
//
//
//
//    @Provides
//    @IntoMap
//    @ClassKey(IntakeCloseCommand.class)
//    public CommandFactory provideIntakeCloseCommand(IntakeSubsystem intakeSubsystem){
//        return new IntakeCloseCommand(intakeSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(IntakeOpenCommand.class)
//    public CommandFactory provideIntakeOpenCommand(IntakeSubsystem intakeSubsystem){
//        return new IntakeOpenCommand(intakeSubsystem);
//
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(IntakeRollInCommand.class)
//    public CommandFactory provideIntakeRollInCommand(IntakeSubsystem intakeSubsystem){
//        return new IntakeRollInCommand(intakeSubsystem);
//
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(IntakeRollOutCommand.class)
//    public CommandFactory provideIntakeRollOutCommand(IntakeSubsystem intakeSubsystem){
//        return new IntakeRollOutCommand(intakeSubsystem);
//    }
//
//
//    @Provides
//    @IntoMap
//    @ClassKey(CubeRollOutCommand.class)
//    public CommandFactory provideCubeRollOutCommand(IntakeSubsystem intakeSubsystem){
//        return new CubeRollOutCommand(intakeSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(IntakeShootCommand.class)
//    public CommandFactory provideIntakeShootCommand(IntakeSubsystem intakeSubsystem, ArmSubsystem armSubsystem) {
//        return new IntakeShootCommand(intakeSubsystem, armSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(ArmUpCommand.class)
//    public CommandFactory provideArmUpCommand(ArmSubsystem armSubsystem) {
//        return new ArmUpCommand(armSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(ArmDownCommand.class)
//    public CommandFactory provideArmDownCommand(ArmSubsystem armSubsystem) {
//        return new ArmDownCommand(armSubsystem);
//    }
//
//
//    @Provides
//    @IntoMap
//    @ClassKey(SetArmPositionCommand.class)
//    public CommandFactory provideSetArmPositionCommand(ArmSubsystem armSubsystem) {
//        return new SetArmPositionCommand(armSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(SetArmPositionHandoffCommand.class)
//    public CommandFactory provideSetArmPositionHandoffCommand(ArmSubsystem armSubsystem) {
//        return new SetArmPositionHandoffCommand(armSubsystem);
//    }
//
//
//
//    @Provides
//    @IntoMap
//    @ClassKey(FieldOrientedDrive.class)
//    public CommandFactory provideFieldOrientedDrive(
//            DrivetrainSubsystem drivetrainSubsystem,
//            @Named(Constants.OIConstants.TRANSLATION_XSUPPLIER) DoubleSupplier translationXSupplier,
//            @Named(Constants.OIConstants.TRANSLATION_YSUPPLIER) DoubleSupplier translationYSupplier,
//            @Named(Constants.OIConstants.THETA_SUPPLIER) DoubleSupplier rotationSupplier) {
//        return new FieldOrientedDrive(drivetrainSubsystem, translationXSupplier, translationYSupplier, rotationSupplier);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(SwerveLockCommand.class)
//    public CommandFactory provideSwerveLockCommand(DrivetrainSubsystem drivetrainSubsystem) {
//        return new SwerveLockCommand(drivetrainSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(CarriageInCommand.class)
//    public CommandFactory provideCarriageInCommand(CarriageSubsystem carriageSubsystem, IntakeSubsystem intakeSubsystem) {
//        return new CarriageInCommand(carriageSubsystem, intakeSubsystem);
//    }
//
//    @Provides
//    @IntoMap
//    @ClassKey(CarriageRollerStopCommand.class)
//    public CommandFactory provideCarriageRollerStopCommand(CarriageSubsystem carriageSubsystem) {
//        return new CarriageRollerStopCommand(carriageSubsystem);
//    }
//
////    @Provides
////    @IntoMap
////    @ClassKey(AprilTagAlignCommand.class)
////    public CommandFactory provideAprilTagAlign(
////            DrivetrainSubsystem drivetrainSubsystem,
////            Limelight visionSubsystem,
////            @Named() PIDController pidControllerX,
////            @Named() IDController pidControllerY,
////            @Named("") PIDController pidControllerAngle
////    ) {
////        return new AprilTagAlignCommand(drivetrainSubsystem, visionSubsystem, pidControllerX, pidControllerY, pidControllerAngle);
////    }
}